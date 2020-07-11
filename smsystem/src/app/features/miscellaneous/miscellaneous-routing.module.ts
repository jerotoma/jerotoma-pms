import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MiscellaneousComponent } from './miscellaneous.component';
import { NotFoundComponent } from './not-found/not-found.component';

import { AuthGuard } from 'app/services';
import { ALL_ROLES } from 'app/models';

const routes: Routes = [
  {
    path: '',
    canActivateChild: [AuthGuard],
    data: { roles: ALL_ROLES },
    component: MiscellaneousComponent,
    children: [
      {
        path: '404',
        canActivateChild: [AuthGuard],
        data: { roles: ALL_ROLES },
        component: NotFoundComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MiscellaneousRoutingModule {
}
