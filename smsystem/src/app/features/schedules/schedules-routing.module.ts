import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SchedulesComponent } from './schedules.component';
import { MeetingTimesViewComponent } from './meeting-times/meeting-times-view.component';
import { WorkDaysComponent } from './work-days/work-days.component';
import { TimetableComponent } from './timetable/timetable.component';
import { AuthGuard } from 'app/services';
import { ALL_ROLES } from 'app/models';

const routes: Routes = [{
  path: '',
  canActivateChild: [AuthGuard],
  data: { roles: ALL_ROLES },
  component: SchedulesComponent,
  children: [
    {
      path: '',
      data: { roles: ALL_ROLES },
      component: MeetingTimesViewComponent,
    },
    {
      path: 'meeting-times',
      data: { roles: ALL_ROLES },
      component: MeetingTimesViewComponent,
    },
    {
      path: 'work-days',
      data: { roles: ALL_ROLES},
      component: WorkDaysComponent,
    },
    {
      path: 'timetable',
      data: { roles: ALL_ROLES },
      component: TimetableComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SchedulesRoutingModule {
}
