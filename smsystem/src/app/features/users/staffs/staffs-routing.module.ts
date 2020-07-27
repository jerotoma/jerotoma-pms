// Modules
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from 'app/services';
import { ALL_ROLES } from 'app/models';

// Components
import { StaffsComponent } from './staffs.component';
import { StaffCreateComponent } from './create/staff-create.component';
import { StaffsViewComponent } from './views/staffs-view.component';
import { StaffShowComponent } from './show/staff-show.component';

// Utilities

const routes: Routes = [
  {
    path: '',
    data: { roles: ALL_ROLES },
    canActivateChild: [AuthGuard],
    component: StaffsComponent,
    children: [
        {
          path: '',
          data: { roles: ALL_ROLES },
          canActivateChild: [AuthGuard],
          component: StaffsViewComponent,
        },
        {
          path: 'create',
          data: { roles: ALL_ROLES },
          canActivateChild: [AuthGuard],
          component: StaffCreateComponent,
        },
        {
          path: ':id',
          data: { roles: ALL_ROLES },
          canActivateChild: [AuthGuard],
          component: StaffShowComponent,
        },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class StudentsRoutingModule {
}
