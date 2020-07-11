// Modules
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Service
import { AuthGuard } from 'app/services/guards/auth-guard.service';
import { USER_ROLE } from 'app/models';
// Components
import { UsersComponent } from './users.component';

const routes: Routes = [{
  path: '',
  canActivateChild: [AuthGuard],
  component: UsersComponent,
  children: [
    {
      path: 'teachers',
      data: { roles: [USER_ROLE.ADMIN, USER_ROLE.TEACHER, USER_ROLE.PRINCIPAL] },
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./teachers/teachers.module')
        .then(m => m.TeachersModule),
    },
    {
      path: 'students',
      canActivateChild: [AuthGuard],
      data: { roles: [USER_ROLE.ADMIN, USER_ROLE.TEACHER, USER_ROLE.PARENT, USER_ROLE.PRINCIPAL] },
      loadChildren: () => import('./students/students.module')
        .then(m => m.StudentsModule),
    },
    {
      path: 'staffs',
      canActivateChild: [AuthGuard],
      data: { roles: [USER_ROLE.ADMIN, USER_ROLE.PARENT, USER_ROLE.PRINCIPAL] },
      loadChildren: () => import('./staffs/staffs.module')
        .then(m => m.StaffsModule),
    },
    {
      path: 'parents',
      canActivateChild: [AuthGuard],
      data: { roles: [USER_ROLE.ADMIN, USER_ROLE.PARENT, USER_ROLE.PRINCIPAL] },
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
