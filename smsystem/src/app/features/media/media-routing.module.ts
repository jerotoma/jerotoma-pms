import {  } from './../../models/securities/user-role.constant';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from 'app/services';
import {
  ALL_ROLES,
  ADMINS_STAFF_TEACHER_AND_EXECUTIVES_ROLES,
  ADMINS_TEACHER_AND_EXECUTIVES_ROLES,
  ADMINS_STUDENT_PARENT_TEACHER_AND_EXECUTIVES_ROLES,
  USER_ROLE } from 'app/models';
import { MediaComponent } from './media.component';
import { MediaListComponent } from './media-list/media-list.component';
import { MediaStudentsComponent } from './media-students/media-students.component';
import { MediaTeachersComponent } from './media-teachers/media-teachers.component';
import { MediaStaffsComponent } from './media-staffs/media-staffs.component';

const routes: Routes = [
  {
    path: '',
    data: { roles: ALL_ROLES},
    canActivateChild: [AuthGuard],
    component: MediaComponent,
    children: [
      {
        path: '',
        data: { roles: ALL_ROLES},
        canActivateChild: [AuthGuard],
        component: MediaListComponent,
      },
      {
        path: 'students',
        data: { roles: ADMINS_STUDENT_PARENT_TEACHER_AND_EXECUTIVES_ROLES},
        canActivateChild: [AuthGuard],
        component: MediaStudentsComponent,
      },
      {
        path: 'teachers',
        data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES},
        canActivateChild: [AuthGuard],
        component: MediaTeachersComponent,
      },
      {
        path: 'staffs',
        data: { roles: ADMINS_STAFF_TEACHER_AND_EXECUTIVES_ROLES},
        canActivateChild: [AuthGuard],
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
