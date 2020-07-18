// Modules
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Service
import { AuthGuard } from 'app/services/guards/auth-guard.service';
import {
  ADMINS_TEACHER_AND_EXECUTIVES_ROLES,
  ADMINS_STAFF_TEACHER_AND_EXECUTIVES_ROLES,
  ADMINS_PARENT_TEACHER_AND_EXECUTIVES_ROLES,
  ADMINS_STUDENT_PARENT_TEACHER_AND_EXECUTIVES_ROLES,
  ADMINS_PARENT_AND_EXECUTIVES_ROLES,
} from 'app/models';
// Components
import { UsersComponent } from './users.component';

const routes: Routes = [{
  path: '',
  canActivateChild: [AuthGuard],
  component: UsersComponent,
  children: [
    {
      path: 'teachers',
      data: { roles: ADMINS_STUDENT_PARENT_TEACHER_AND_EXECUTIVES_ROLES },
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./teachers/teachers.module')
        .then(m => m.TeachersModule),
    },
    {
      path: 'students',
      canActivateChild: [AuthGuard],
      data: { roles: ADMINS_PARENT_TEACHER_AND_EXECUTIVES_ROLES},
      loadChildren: () => import('./students/students.module')
        .then(m => m.StudentsModule),
    },
    {
      path: 'staffs',
      canActivateChild: [AuthGuard],
      data: { roles: ADMINS_STAFF_TEACHER_AND_EXECUTIVES_ROLES },
      loadChildren: () => import('./staffs/staffs.module')
        .then(m => m.StaffsModule),
    },
    {
      path: 'parents',
      canActivateChild: [AuthGuard],
      data: { roles: ADMINS_STUDENT_PARENT_TEACHER_AND_EXECUTIVES_ROLES  },
      loadChildren: () => import('./parents/parents.module')
      .then(m => m.ParentsModule),
    },
    {
      path: '',
      redirectTo: 'users',
      pathMatch: 'full',
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {
}
