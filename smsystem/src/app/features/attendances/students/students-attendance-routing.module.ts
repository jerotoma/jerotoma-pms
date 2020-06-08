import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { StudentsAttendanceComponent } from './students-attendance.component';

const routes: Routes = [
  {
    path: '',
    component: StudentsAttendanceComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class StudentsAttendanceRoutingModule { }
