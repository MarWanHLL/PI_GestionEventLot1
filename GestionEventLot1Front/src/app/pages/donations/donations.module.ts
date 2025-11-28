import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { DonationsRoutingModule } from './donations-routing.module';
import { DonationListComponent } from './donation-list/donation-list.component';
import { DonationDetailComponent } from './donation-detail/donation-detail.component';

@NgModule({
  declarations: [DonationListComponent, DonationDetailComponent],
  imports: [CommonModule, FormsModule, DonationsRoutingModule],
})
export class DonationsModule {}
