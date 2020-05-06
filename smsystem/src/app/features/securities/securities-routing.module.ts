import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { SecuritiesComponent } from './securities.component';
import { RolesComponent } from './roles/roles.component';
import { PermissionsComponent } from './permissions/permissions.component';
import { AuthGuard } from 'app/services/guards/auth-guard.service';

const routes: Routes = [{
  path: '',
  canActivateChild: [AuthGuard],
  component: SecuritiesComponent,
  children: [
    {
      path: '',
      component: SecuritiesComponent,
    },
    {
      path: 'roles',
      component: RolesComponent,
    },
    {
      path: 'permissions',
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
