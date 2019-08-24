// Modules
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Service
import { AuthGuard } from 'app/services/guards/auth-guard.service';

// Components
import { UsersComponent } from './users.component';
import { Tab1Component, Tab2Component, ParentsComponent } from './parents/parents.component';
import { StaffsComponent } from './staffs/staffs.component';
import { InfiniteListComponent } from './infinite-list/infinite-list.component';
import { TeachersComponent } from './teachers/teachers.component';

const routes: Routes = [{
  path: '',
  canActivateChild: [AuthGuard],
  component: UsersComponent,
  children: [
    {
      path: 'teachers',
      component: TeachersComponent,
    },
    {
      path: 'students',
      loadChildren: () => import('./students/students.module')
        .then(m => m.StudentsModule),
    },
    {
      path: 'infinite-list',
      component: InfiniteListComponent,
    },
    {
      path: 'staffs',
      component: StaffsComponent,
    },
    {
      path: 'parents',
      component: ParentsComponent,
      children: [
        {
          path: '',
          redirectTo: 'tab1',
          pathMatch: 'full',
        },
        {
          path: 'tab1',
          component: Tab1Component,
        },
        {
          path: 'tab2',
          component: Tab2Component,
        },
      ],
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
