import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ALL_ROLES } from 'app/models';
import { AuthGuard } from 'app/services';

import { ProfileComponent } from './profile/profile.component';
import { PreferencesComponent } from './preferences/preferences.component';


const routes: Routes = [
  {
    path: 'profile',
    canActivate: [AuthGuard],
    data: {roles: ALL_ROLES},
    component: ProfileComponent,
  },
  {
    path: 'preferences',
    canActivate: [AuthGuard],
    data: {roles: ALL_ROLES},
    component: PreferencesComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HeaderRoutingModule {
}
