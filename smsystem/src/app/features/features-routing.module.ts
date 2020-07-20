import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { FeaturesComponent } from './features.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NotFoundComponent } from './miscellaneous/not-found/not-found.component';
import { AuthGuard } from 'app/services';
import {
  ALL_ROLES,
  USER_ROLE,
  ADMINS_AND_EXECUTIVES_ROLES,
  ADMINS_TEACHER_AND_EXECUTIVES_ROLES,
  ADMINS_STAFF_TEACHER_AND_EXECUTIVES_ROLES,
  STUDENT_AND_TEACHER_ROLES} from 'app/models';

const routes: Routes = [{
  path: '',
  canActivateChild: [AuthGuard],
  component: FeaturesComponent,
  children: [
    {
      path: '',
      data: { roles: ALL_ROLES },
      canActivateChild: [AuthGuard],
      component: DashboardComponent,
    },
    {
      path: 'users',
      data: { roles:  ALL_ROLES },
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./users/users.module')
        .then(m => m.UsersModule),
    },
    {
      path: 'resources',
      data: { roles: ALL_ROLES },
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./resources/resources.module')
        .then(m => m.ResourcesModule),
    },
    {
      path: 'my-schedules',
      data: { roles: [USER_ROLE.STUDENT], },
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./my-schedules/my-schedules.module')
        .then(m => m.MySchedulesModule ),
    },
    {
      path: 'my-courses',
      data: { roles: STUDENT_AND_TEACHER_ROLES },
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./my-courses/my-courses.module')
        .then(m => m.MyCoursesModule),
    },
    {
      path: 'system-setup',
      data: { roles: ADMINS_AND_EXECUTIVES_ROLES },
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./system-setup/system-setup.module')
        .then(m => m.SystemSetupModule),
    },
    {
      path: 'account',
      data: { roles: ALL_ROLES },
      canActivateChild: [AuthGuard],
      loadChildren: () => import('app/layouts/header/header.module')
        .then(m => m.HeaderModule),
    },
    {
      path: 'admissions',
      data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES },
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./admissions/admissions.module')
        .then(m => m.AdmissionsModule),
    },
    {
      path: 'miscellaneous',
      data: { roles: ALL_ROLES },
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./miscellaneous/miscellaneous.module')
        .then(m => m.MiscellaneousModule),
    },
    {
      path: 'securities',
      data: { roles: ADMINS_AND_EXECUTIVES_ROLES },
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./securities/securities.module')
        .then(m => m.SecuritiesModule),
    },
    {
      path: 'schedules',
      data: { roles: ALL_ROLES },
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./schedules/schedules.module')
        .then(m => m.SchedulesModule),
    },
    {
      path: 'attendances',
      data: { roles: ALL_ROLES },
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./attendances/attendances.module')
        .then(m => m.AttendancesModule),
    },
    {
      path: 'media',
      data: { roles: ALL_ROLES },
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./media/media.module')
        .then(m => m.MediaModule),
    },
    {
      path: '',
      redirectTo: 'dashboard',
      pathMatch: 'full',
    },
    {
      path: '**',
      component: NotFoundComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class FeaturesRoutingModule {
}
