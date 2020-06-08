import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


import { TeachersAttendanceComponent } from './teachers-attendance.component';

const routes: Routes = [
  {
    path: '',
    component: TeachersAttendanceComponent,
  },
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TeachersAttendenceRoutingModule { }
