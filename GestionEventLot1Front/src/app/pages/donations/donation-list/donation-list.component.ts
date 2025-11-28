import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  Donation,
  DonationCreatePayload,
  DonationStatus,
} from '../../../core/models/donation.model';
import { DonationService } from '../../../core/services/donation.service';
import { ProductCategory } from '../../../core/models/association.model';

@Component({
  selector: 'app-donation-list',
  templateUrl: './donation-list.component.html',
})
export class DonationListComponent implements OnInit {
  donations: Donation[] = [];
  loading = false;
  error: string | null = null;

  categories = Object.values(ProductCategory);

  newDonation: DonationCreatePayload = {
    merchantId: 0,
    merchantName: '',
    merchantAddress: '',
    totalQuantity: 0,
    quantityUnit: 'kg',
    estimatedValue: 0,
    pickupPlannedAt: null,
    category: ProductCategory.FRUITS_VEGETABLES,
  };

  saving = false;

  // === Traçabilité modal state ===
  traceModalOpen = false;
  traceDonation: Donation | null = null;
  DonationStatus = DonationStatus; // expose enum to template

  constructor(
    private donationService: DonationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadDonations();
  }

  loadDonations(): void {
    this.loading = true;
    this.error = null;

    this.donationService.getAll().subscribe({
      next: (data) => {
        this.donations = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.error = 'Erreur lors du chargement des dons.';
        this.loading = false;
      },
    });
  }

  createDonation(): void {
    if (!this.newDonation.merchantName) {
      return;
    }

    this.saving = true;
    this.error = null;

    if (!this.newDonation.merchantId) {
      this.newDonation.merchantId = Date.now(); // simple fake ID for demo
    }

    this.donationService.create(this.newDonation).subscribe({
      next: (created) => {
        this.saving = false;
        this.newDonation = {
          merchantId: 0,
          merchantName: '',
          merchantAddress: '',
          totalQuantity: 0,
          quantityUnit: 'kg',
          estimatedValue: 0,
          pickupPlannedAt: null,
          category: ProductCategory.FRUITS_VEGETABLES,
        };
        this.donations.push(created);
      },
      error: (err) => {
        console.error(err);
        this.error = 'Erreur lors de la création du don.';
        this.saving = false;
      },
    });
  }

  goToDetails(donation: Donation): void {
    this.router.navigate(['/donations', donation.id]);
  }

  runMatching(donation: Donation): void {
    this.donationService.matchDonation(donation.id).subscribe({
      next: (result) => {
        console.log('Match result', result);
        this.loadDonations();
      },
      error: (err) => {
        console.error(err);
        this.error = 'Erreur lors du matching intelligent.';
      },
    });
  }

  statusBadgeClasses(status: DonationStatus): string {
    switch (status) {
      case DonationStatus.CREATED:
        return 'bg-slate-100 text-slate-700';
      case DonationStatus.MATCHED:
        return 'bg-amber-100 text-amber-700';
      case DonationStatus.IN_TRANSIT:
        return 'bg-blue-100 text-blue-700';
      case DonationStatus.DELIVERED:
        return 'bg-emerald-100 text-emerald-700';
      case DonationStatus.CANCELLED:
        return 'bg-red-100 text-red-700';
      default:
        return 'bg-slate-100 text-slate-700';
    }
  }

  // ===== Traçabilité helpers =====

  openTraceModal(donation: Donation): void {
    this.traceDonation = donation;
    this.traceModalOpen = true;
  }

  closeTraceModal(): void {
    this.traceModalOpen = false;
    this.traceDonation = null;
  }

  /** 0 = chez commerçant, 1 = matché, 2 = en transit, 3 = livré */
  getTraceStep(d: Donation): number {
    switch (d.status) {
      case DonationStatus.CREATED:
        return 0;
      case DonationStatus.MATCHED:
        return 1;
      case DonationStatus.IN_TRANSIT:
        return 2;
      case DonationStatus.DELIVERED:
        return 3;
      case DonationStatus.CANCELLED:
        return 0;
      default:
        return 0;
    }
  }

  /** car position on the dotted line, as percentage string */
  getCarLeftPercent(d: Donation): string {
    const step = this.getTraceStep(d);
    const percentages = ['5%', '35%', '65%', '90%'];
    return percentages[Math.min(step, percentages.length - 1)];
  }

  /** whether to highlight the house icon */
  isDelivered(d: Donation): boolean {
    return d.status === DonationStatus.DELIVERED;
  }

  /** friendly label of current location */
  getLocationLabel(d: Donation): string {
    switch (d.status) {
      case DonationStatus.CREATED:
        return 'Chez le commerçant';
      case DonationStatus.MATCHED:
        return 'Association trouvée, en attente de prise en charge';
      case DonationStatus.IN_TRANSIT:
        return 'En cours de transport vers l’association';
      case DonationStatus.DELIVERED:
        return 'Livré à l’association';
      case DonationStatus.CANCELLED:
        return 'Don annulé';
      default:
        return d.currentLocation || 'Statut inconnu';
    }
  }
}
