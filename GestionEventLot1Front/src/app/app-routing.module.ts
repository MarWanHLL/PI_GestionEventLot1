import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: 'donations', pathMatch: 'full' },

  {
    path: 'donations',
    loadChildren: () =>
      import('./pages/donations/donations.module').then(
        (m) => m.DonationsModule
      ),
  },
  {
    path: 'associations',
    loadChildren: () =>
      import('./pages/associations/associations.module').then(
        (m) => m.AssociationsModule
      ),
  },
  {
    path: 'events',
    loadChildren: () =>
      import('./pages/events/events.module').then((m) => m.EventsModule),
  },

  { path: '**', redirectTo: 'donations' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
