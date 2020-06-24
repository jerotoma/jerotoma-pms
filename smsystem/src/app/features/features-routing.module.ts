import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { FeaturesComponent } from './features.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NotFoundComponent } from './miscellaneous/not-found/not-found.component';
import { AuthGuard } from 'app/services';

const routes: Routes = [{
  path: '',
  canActivateChild: [AuthGuard],
  component: FeaturesComponent,
  children: [
    {
      path: '',
      component: DashboardComponent,
    },
    {
      path: 'users',
      loadChildren: () => import('./users/users.module')
        .then(m => m.UsersModule),
    },
    {
      path: 'system-setup',
      loadChildren: () => import('./system-setup/system-setup.module')
        .then(m => m.SystemSetupModule),
    },
    {
      path: 'account',
      loadChildren: () => import('app/layouts/header/header.module')
        .then(m => m.HeaderModule),
    },
    {
      path: 'admissions',
      loadChildren: () => import('./admissions/admissions.module')
        .then(m => m.AdmissionsModule),
    },
    {
      path: 'miscellaneous',
      loadChildren: () => import('./miscellaneous/miscellaneous.module')
        .then(m => m.MiscellaneousModule),
    },
    {
      path: 'securities',
      loadChildren: () => import('./securities/securities.module')
        .then(m => m.SecuritiesModule),
    },
    {
      path: 'schedules',
      loadChildren: () => import('./schedules/schedules.module')
        .then(m => m.SchedulesModule),
    },
    {
      path: 'attendances',
      loadChildren: () => import('./attendances/attendances.module')
        .then(m => m.AttendancesModule),
    },
    {
      path: 'media',
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
