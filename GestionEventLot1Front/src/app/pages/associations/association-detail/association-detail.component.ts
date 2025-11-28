import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  Association,
  AssociationCreatePayload,
} from '../../../core/models/association.model';
import { AssociationService } from '../../../core/services/association.service';

@Component({
  selector: 'app-association-detail',
  templateUrl: './association-detail.component.html',
})
export class AssociationDetailComponent implements OnInit {
  associationId!: number;
  association: Association | null = null;
  formModel: AssociationCreatePayload = {
    name: '',
    address: '',
    city: '',
    zone: '',
    location: '',
  };

  loading = false;
  saving = false;
  deleting = false;
  error: string | null = null;
  success: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private associationService: AssociationService
  ) {}

  ngOnInit(): void {
    this.associationId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadAssociation();
  }

  loadAssociation(): void {
    this.loading = true;
    this.error = null;

    this.associationService.getById(this.associationId).subscribe({
      next: (a) => {
        this.association = a;
        this.formModel = {
          name: a.name,
          address: a.address,
          city: a.city,
          zone: a.zone,
          location: a.location ?? '',
        };
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.error = 'Impossible de charger l’association.';
        this.loading = false;
      },
    });
  }

  update(): void {
    if (!this.association) {
      return;
    }

    this.saving = true;
    this.error = null;
    this.success = null;

    this.associationService
      .update(this.associationId, this.formModel)
      .subscribe({
        next: (updated) => {
          this.association = updated;
          this.success = 'Association mise à jour avec succès.';
          this.saving = false;
        },
        error: (err) => {
          console.error(err);
          this.error = 'Erreur lors de la mise à jour.';
          this.saving = false;
        },
      });
  }

  delete(): void {
    if (!confirm('Voulez-vous vraiment supprimer cette association ?')) {
      return;
    }

    this.deleting = true;
    this.error = null;

    this.associationService.delete(this.associationId).subscribe({
      next: () => {
        this.deleting = false;
        // back to list
        this.router.navigate(['/associations']);
      },
      error: (err) => {
        console.error(err);
        this.error = 'Erreur lors de la suppression.';
        this.deleting = false;
      },
    });
  }
}
