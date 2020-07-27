import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from 'app/services';
import { MyAttendancesComponent } from './my-attendances.component';
import { AttendanceSummaryComponent } from './attendance-summary/attendance-summary.component';
import { AttendanceReportDetailsComponent } from './attendance-report-details/attendance-report-details.component';
import { STUDENT_AND_TEACHER_ROLES, ALL_ROLES } from 'app/models';

const routes: Routes = [
  {
    path: '',
    canActivateChild: [AuthGuard],
    data: { roles: STUDENT_AND_TEACHER_ROLES},
    component: MyAttendancesComponent,
    children: [
      {
        path: '',
        data: { roles: ALL_ROLES },
        component: AttendanceSummaryComponent,
      },
      {
        path: 'report-details',
        data: { roles: ALL_ROLES },
        component: AttendanceReportDetailsComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MyAttendancesRoutingModule { }
