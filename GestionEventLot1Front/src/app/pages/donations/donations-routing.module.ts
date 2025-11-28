import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DonationListComponent } from './donation-list/donation-list.component';
import { DonationDetailComponent } from './donation-detail/donation-detail.component';

const routes: Routes = [
  { path: '', component: DonationListComponent },
  { path: ':id', component: DonationDetailComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DonationsRoutingModule {}
