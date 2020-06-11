import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from 'app/services';
import { AttendancesComponent } from './attendances.component';

const routes: Routes = [
  {
    path: '',
    canActivateChild: [AuthGuard],
    component: AttendancesComponent,
    children: [
      {
        path: 'classes',
        loadChildren: () => import('./classes/classes-attendance.module')
        .then(m => m.ClassesAttendanceModule),
      },
      {
        path: 'students',
        loadChildren: () => import('./students/students-attendance.module')
        .then(m => m.StudentsAttendanceModule),
      },
      {
        path: 'teachers',
        loadChildren: () => import('./teachers/teachers-attendance.module')
          .then(m => m.TeachersAttendenceModule),
      },
      {
        path: 'staffs',
        loadChildren: () => import('./staffs/staffs-attendance.module')
          .then(m => m.StaffsAttendanceModule),
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AttendancesRoutingModule { }
