import { AfterViewInit, Component, OnInit } from '@angular/core';
import {
  Association,
  AssociationCreatePayload,
} from '../../../core/models/association.model';
import { AssociationService } from '../../../core/services/association.service';
import * as L from 'leaflet';

@Component({
  selector: 'app-association-list',
  templateUrl: './association-list.component.html',
})
export class AssociationListComponent implements OnInit, AfterViewInit {
  associations: Association[] = [];
  loading = false;
  error: string | null = null;

  newAssociation: AssociationCreatePayload = {
    name: '',
    address: '',
    city: '',
    zone: '',
    location: '', // will hold "lat,lng"
  };

  private map: L.Map | null = null;
  private marker: L.Marker | null = null;

  constructor(private associationService: AssociationService) {}

  ngOnInit(): void {
    this.loadAssociations();
  }

  ngAfterViewInit(): void {
    this.initMap();
  }

  private initMap(): void {
    if (this.map) {
      return; // already initialized
    }

    this.map = L.map('association-map', {
      center: [36.8065, 10.1815], // Tunis as default center
      zoom: 11,
    });

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors',
    }).addTo(this.map);

    // Handle click events on the map
    this.map.on('click', (event: L.LeafletMouseEvent) => {
      const { lat, lng } = event.latlng;

      // Update form model
      this.newAssociation.location = `${lat.toFixed(6)}, ${lng.toFixed(6)}`;

      // Place / move marker
      if (this.marker) {
        this.marker.setLatLng(event.latlng);
      } else {
        this.marker = L.marker(event.latlng).addTo(this.map!);
      }
    });
  }

  loadAssociations(): void {
    this.loading = true;
    this.error = null;

    this.associationService.getAll().subscribe({
      next: (data) => {
        this.associations = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.error = 'Erreur lors du chargement des associations.';
        this.loading = false;
      },
    });
  }

  createAssociation(): void {
    if (!this.newAssociation.name) {
      return;
    }

    this.associationService.create(this.newAssociation).subscribe({
      next: (created) => {
        this.newAssociation = {
          name: '',
          address: '',
          city: '',
          zone: '',
          location: '',
        };

        // Clear marker when form is reset
        if (this.marker) {
          this.marker.remove();
          this.marker = null;
        }

        this.associations.push(created);
      },
      error: (err) => {
        console.error(err);
        this.error = 'Erreur lors de la création de l’association.';
      },
    });
  }

  /** Open Google Maps for an existing association */
  openOnMap(a: Association): void {
    const base = (a.location || `${a.address ?? ''} ${a.city ?? ''}`).trim();
    if (!base) {
      return;
    }
    const query = encodeURIComponent(base);
    window.open(
      `https://www.google.com/maps/search/?api=1&query=${query}`,
      '_blank'
    );
  }
}
