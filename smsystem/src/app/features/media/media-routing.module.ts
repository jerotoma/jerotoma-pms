import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from 'app/services';
import { MediaComponent } from './media.component';


const routes: Routes = [
  {
    path: '',
    canActivateChild: [AuthGuard],
    component: MediaComponent,
    children: [
      {
        path: '',
        component: MediaComponent,
      },
      {
        path: 'students',
        component: MediaComponent,
      },
      {
        path: 'teachers',
        component: MediaComponent,
      },
      {
        path: 'staffs',
        component: MediaComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MediaRoutingModule { }
