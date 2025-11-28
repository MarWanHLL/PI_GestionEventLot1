import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EventTimelineComponent } from './event-timeline/event-timeline.component';

const routes: Routes = [
  // later we'll use :donationId to show timeline for one donation
  { path: ':donationId', component: EventTimelineComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EventsRoutingModule {}
