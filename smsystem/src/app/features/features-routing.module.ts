import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { FeaturesComponent } from './features.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ECommerceComponent } from './resources/e-commerce.component';
import { NotFoundComponent } from './miscellaneous/not-found/not-found.component';
import { AuthGuard } from '../services/guards/auth-guard.service';

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
      path: 'resources',
      component: ECommerceComponent,
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
      loadChildren: () => import('../@theme/components/header/header.module')
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
