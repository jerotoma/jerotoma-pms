import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { SecuritiesComponent } from './securities.component';
import { RolesComponent } from './roles/roles.component';
import { PermissionsComponent } from './permissions/permissions.component';
import { AuthGuard } from 'app/services';
import { ADMINS_TEACHER_AND_EXECUTIVES_ROLES } from 'app/models';

const routes: Routes = [{
  path: '',
  canActivateChild: [AuthGuard],
  data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES},
  component: SecuritiesComponent,
  children: [
    {
      path: '',
      canActivateChild: [AuthGuard],
      data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES},
      component: SecuritiesComponent,
    },
    {
      path: 'roles',
      canActivateChild: [AuthGuard],
      data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES},
      component: RolesComponent,
    },
    {
      path: 'permissions',
      canActivateChild: [AuthGuard],
      data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES},
      component: PermissionsComponent,
    },
    {
      path: '',
      redirectTo: 'securities',
      pathMatch: 'full',
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SecuritiesRoutingModule {
}
