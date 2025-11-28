import { Component, OnInit } from '@angular/core';
import { forkJoin, of } from 'rxjs';
import { switchMap } from 'rxjs/operators';

import { Donation, DonationStatus } from '../../../core/models/donation.model';
import {
  Association,
  AssociationNeed,
  ProductCategory,
} from '../../../core/models/association.model';

import { DonationService } from '../../../core/services/donation.service';
import { AssociationService } from '../../../core/services/association.service';

interface StatusStat {
  status: DonationStatus;
  count: number;
}

interface CategoryStat {
  category: ProductCategory;
  count: number;
  quantity: number;
}

interface ZoneStat {
  zone: string;
  count: number;
}

interface CoverageStat {
  category: ProductCategory;
  needed: number;
  donated: number;
  coverage: number; // 0–100 %
}

@Component({
  selector: 'app-event-timeline',
  templateUrl: './event-timeline.component.html',
})
export class EventTimelineComponent implements OnInit {
  loading = false;
  error: string | null = null;

  donations: Donation[] = [];
  associations: Association[] = [];
  needs: AssociationNeed[] = [];

  // Top KPIs
  totalDonations = 0;
  totalQuantity = 0;
  totalEstimatedValue = 0;
  deliveredRate = 0;

  // Distributions
  statusStats: StatusStat[] = [];
  categoryStats: CategoryStat[] = [];
  zoneStats: ZoneStat[] = [];
  coverageByCategory: CoverageStat[] = [];

  DonationStatus = DonationStatus;
  ProductCategory = ProductCategory;

  constructor(
    private donationService: DonationService,
    private associationService: AssociationService
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  private loadData(): void {
    this.loading = true;
    this.error = null;

    forkJoin({
      donations: this.donationService.getAll(),
      associations: this.associationService.getAll(),
    })
      .pipe(
        switchMap(({ donations, associations }) => {
          this.donations = donations;
          this.associations = associations;

          if (!associations.length) {
            return of<AssociationNeed[][]>([]);
          }

          // load needs for each association
          const needCalls = associations.map((a) =>
            this.associationService.getNeeds(a.id)
          );

          return forkJoin(needCalls);
        })
      )
      .subscribe({
        next: (needsPerAssociation) => {
          this.needs = ([] as AssociationNeed[]).concat(
            ...(needsPerAssociation || [])
          );
          this.computeStats();
          this.loading = false;
        },
        error: (err) => {
          console.error(err);
          this.error = 'Erreur lors du chargement des statistiques.';
          this.loading = false;
        },
      });
  }

  private computeStats(): void {
    // === global KPIs ===
    this.totalDonations = this.donations.length;
    this.totalQuantity = this.donations.reduce(
      (sum, d) => sum + (d.totalQuantity || 0),
      0
    );
    this.totalEstimatedValue = this.donations.reduce(
      (sum, d) => sum + (d.estimatedValue || 0),
      0
    );

    const deliveredCount = this.donations.filter(
      (d) => d.status === DonationStatus.DELIVERED
    ).length;
    this.deliveredRate = this.totalDonations
      ? (deliveredCount / this.totalDonations) * 100
      : 0;

    // === status distribution ===
    this.statusStats = Object.values(DonationStatus)
      .map((status) => ({
        status,
        count: this.donations.filter((d) => d.status === status).length,
      }))
      .filter((s) => s.count > 0);

    // === category distribution ===
    this.categoryStats = Object.values(ProductCategory)
      .map((cat) => {
        const donationsOfCat = this.donations.filter((d) => d.category === cat);
        return {
          category: cat,
          count: donationsOfCat.length,
          quantity: donationsOfCat.reduce(
            (sum, d) => sum + (d.totalQuantity || 0),
            0
          ),
        };
      })
      .filter((c) => c.count > 0);

    // === associations by zone ===
    const zoneMap = new Map<string, number>();
    this.associations.forEach((a) => {
      const zone = a.zone || 'Non précisée';
      zoneMap.set(zone, (zoneMap.get(zone) || 0) + 1);
    });

    this.zoneStats = Array.from(zoneMap.entries()).map(([zone, count]) => ({
      zone,
      count,
    }));

    // === coverage: donations vs needs per category ===
    const neededByCat = new Map<ProductCategory, number>();
    this.needs.forEach((n) => {
      const cat = n.category;
      const max = n.maxQuantity || 0;
      neededByCat.set(cat, (neededByCat.get(cat) || 0) + max);
    });

    const donatedByCat = new Map<ProductCategory, number>();
    this.donations.forEach((d) => {
      const cat = d.category;
      const q = d.totalQuantity || 0;
      donatedByCat.set(cat, (donatedByCat.get(cat) || 0) + q);
    });

    this.coverageByCategory = Array.from(neededByCat.entries()).map(
      ([cat, needed]) => {
        const donated = donatedByCat.get(cat) || 0;
        const coverage = needed ? Math.min(100, (donated / needed) * 100) : 0;
        return { category: cat, needed, donated, coverage };
      }
    );
  }

  // helpers for bar widths
  maxStatusCount(): number {
    return this.statusStats.reduce((max, s) => Math.max(max, s.count), 0) || 1;
  }

  maxCategoryQuantity(): number {
    return (
      this.categoryStats.reduce((max, c) => Math.max(max, c.quantity), 0) || 1
    );
  }

  maxZoneCount(): number {
    return this.zoneStats.reduce((max, z) => Math.max(max, z.count), 0) || 1;
  }
}
