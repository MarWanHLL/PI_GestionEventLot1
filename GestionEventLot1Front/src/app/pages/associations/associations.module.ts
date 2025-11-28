import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AssociationsRoutingModule } from './associations-routing.module';
import { AssociationListComponent } from './association-list/association-list.component';
import { AssociationDetailComponent } from './association-detail/association-detail.component';

@NgModule({
  declarations: [AssociationListComponent, AssociationDetailComponent],
  imports: [
    CommonModule,
    FormsModule, // ✅ this is the important line
    AssociationsRoutingModule,
  ],
})
export class AssociationsModule {}
