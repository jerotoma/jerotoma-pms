import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ClassesAttendanceComponent } from './classes-attendance.component';

const routes: Routes = [
  {
    path: '',
    component: ClassesAttendanceComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ClassesAttendanceRoutingModule { }
