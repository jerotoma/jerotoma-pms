import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from 'app/services';
import { AttendancesComponent } from './attendances.component';
import { ADMINS_TEACHER_AND_EXECUTIVES_ROLES, ADMINS_AND_EXECUTIVES_ROLES, ADMINS_STUDENT_PARENT_TEACHER_AND_EXECUTIVES_ROLES } from 'app/models';

const routes: Routes = [
  {
    path: '',
    canActivateChild: [AuthGuard],
    data: { roles: ADMINS_STUDENT_PARENT_TEACHER_AND_EXECUTIVES_ROLES },
    component: AttendancesComponent,
    children: [
      {
        path: 'classes',
        canActivateChild: [AuthGuard],
        data: { roles: ADMINS_STUDENT_PARENT_TEACHER_AND_EXECUTIVES_ROLES},
        loadChildren: () => import('./classes/classes-attendance.module')
        .then(m => m.ClassesAttendanceModule),
      },
      {
        path: 'students',
        canActivateChild: [AuthGuard],
        data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES},
        loadChildren: () => import('./students/students-attendance.module')
        .then(m => m.StudentsAttendanceModule),
      },
      {
        path: 'teachers',
        canActivateChild: [AuthGuard],
        data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES},
        loadChildren: () => import('./teachers/teachers-attendance.module')
          .then(m => m.TeachersAttendenceModule),
      },
      {
        path: 'staffs',
        canActivateChild: [AuthGuard],
        data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES},
        loadChildren: () => import('./staffs/staffs-attendance.module')
          .then(m => m.StaffsAttendanceModule),
      },
      {
        path: 'statuses',
        canActivateChild: [AuthGuard],
        data: { roles: ADMINS_AND_EXECUTIVES_ROLES},
        loadChildren: () => import('./statuses/attendance-statuses.module')
          .then(m => m.AttendanceStatusesModule),
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AttendancesRoutingModule { }
