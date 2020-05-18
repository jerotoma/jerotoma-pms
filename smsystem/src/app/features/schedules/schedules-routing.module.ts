import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SchedulesComponent } from './schedules.component';
import { MeetingTimesViewComponent } from './meeting-times/meeting-times-view.component';
import { WorkDaysComponent } from './work-days/work-days.component';

const routes: Routes = [{
  path: '',
  component: SchedulesComponent,
  children: [
    {
      path: '',
      component: MeetingTimesViewComponent,
    },
    {
      path: 'meeting-times',
      component: MeetingTimesViewComponent,
    },
    {
      path: 'work-days',
      component: WorkDaysComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SchedulesRoutingModule {
}
