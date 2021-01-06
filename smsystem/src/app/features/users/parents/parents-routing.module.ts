// Modules
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Service
import { AuthGuard } from 'app/services';
import { ALL_ROLES } from 'app/models';

// Components
import { ParentsComponent } from './parents.component';
import { ParentShowComponent } from './show/parent-show.component';
import { ParentsViewComponent } from './views/parents-view.component';

// Utilities

const routes: Routes = [
  {
    path: '',
    data: { roles: ALL_ROLES },
    canActivateChild: [AuthGuard],
    component: ParentsComponent,
    children: [
        {
          path: '',
          data: { roles: ALL_ROLES },
          canActivateChild: [AuthGuard],
          component: ParentsViewComponent,
        },
        {
          path: ':id',
          data: { roles: ALL_ROLES },
          canActivateChild: [AuthGuard],
          component: ParentShowComponent,
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
