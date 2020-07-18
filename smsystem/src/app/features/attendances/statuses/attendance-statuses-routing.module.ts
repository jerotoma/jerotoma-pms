import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AttendanceStatusesComponent } from './attendance-statuses.component';

const routes: Routes = [
  {
    path: '',
    component: AttendanceStatusesComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AttendanceStatusesRoutingModule { }
