import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { StaffsAttendanceComponent } from './staffs-attendance.component';

const routes: Routes = [
  {
    path: '',
    component: StaffsAttendanceComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class StaffsAttendanceRoutingModule { }
