import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from 'app/services';
import { MediaComponent } from './media.component';
import { MediaListComponent } from './media-list/media-list.component';
import { MediaStudentsComponent } from './media-students/media-students.component';
import { MediaTeachersComponent } from './media-teachers/media-teachers.component';
import { MediaStaffsComponent } from './media-staffs/media-staffs.component';

const routes: Routes = [
  {
    path: '',
    canActivateChild: [AuthGuard],
    component: MediaComponent,
    children: [
      {
        path: '',
        component: MediaListComponent,
      },
      {
        path: 'students',
        component: MediaStudentsComponent,
      },
      {
        path: 'teachers',
        component: MediaTeachersComponent,
      },
      {
        path: 'staffs',
        component: MediaStaffsComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MediaRoutingModule { }
