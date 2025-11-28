import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AssociationListComponent } from './association-list/association-list.component';
import { AssociationDetailComponent } from './association-detail/association-detail.component';

const routes: Routes = [
  { path: '', component: AssociationListComponent },
  { path: ':id', component: AssociationDetailComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AssociationsRoutingModule {}
