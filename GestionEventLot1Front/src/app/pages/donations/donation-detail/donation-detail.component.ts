import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Donation, DonationStatus } from '../../../core/models/donation.model';
import { DonationService } from '../../../core/services/donation.service';

@Component({
  selector: 'app-donation-detail',
  templateUrl: './donation-detail.component.html',
})
export class DonationDetailComponent implements OnInit {
  donation: Donation | null = null;
  loading = false;
  error: string | null = null;

  DonationStatus = DonationStatus; // expose enum to template

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private donationService: DonationService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) {
      this.error = 'Identifiant du don invalide.';
      return;
    }

    this.loadDonation(id);
  }

  loadDonation(id: number): void {
    this.loading = true;
    this.error = null;

    this.donationService.getById(id).subscribe({
      next: (d) => {
        this.donation = d;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.error = 'Erreur lors du chargement du don.';
        this.loading = false;
      },
    });
  }

  goBack(): void {
    this.router.navigate(['/donations']);
  }

  // ===== Traçabilité logic =====

  /** 0 = chez commerçant, 1 = matché, 2 = en transit, 3 = livré */
  getTraceStep(): number {
    if (!this.donation) return 0;

    switch (this.donation.status) {
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

  /** position de la voiture le long de la ligne en % */
  getCarLeftPercent(): string {
    const step = this.getTraceStep();
    const percentages = ['5%', '35%', '65%', '90%']; // ajuste si besoin
    return percentages[Math.min(step, percentages.length - 1)];
  }

  isDelivered(): boolean {
    return this.donation?.status === DonationStatus.DELIVERED;
  }

  getLocationLabel(): string {
    if (!this.donation) return '';

    switch (this.donation.status) {
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
        return this.donation.currentLocation || 'Statut inconnu';
    }
  }
}
