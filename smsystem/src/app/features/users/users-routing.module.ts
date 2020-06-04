// Modules
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Service
import { AuthGuard } from 'app/services/guards/auth-guard.service';

// Components
import { UsersComponent } from './users.component';

const routes: Routes = [{
  path: '',
  canActivateChild: [AuthGuard],
  component: UsersComponent,
  children: [
    {
      path: 'teachers',
      loadChildren: () => import('./teachers/teachers.module')
        .then(m => m.TeachersModule),
    },
    {
      path: 'students',
      loadChildren: () => import('./students/students.module')
        .then(m => m.StudentsModule),
    },
    {
      path: 'staffs',
      loadChildren: () => import('./staffs/staffs.module')
        .then(m => m.StaffsModule),
    },
    {
      path: 'parents',
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
