import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MeetingTimesComponent } from './meeting-times.component';
import { MeetingTimesViewComponent } from './meeting-times-view/meeting-times-view.component';

const routes: Routes = [{
  path: '',
  component: MeetingTimesComponent,
  children: [
    {
      path: '',
      component: MeetingTimesViewComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MeetingTimesRoutingModule {
}
