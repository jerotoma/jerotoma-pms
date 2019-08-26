// Modules
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Service
import { AuthGuard } from 'app/services/guards/auth-guard.service';

// Components
import { StaffsComponent } from './staffs.component';
import { StaffCreateComponent } from './create/staff-create.component';
import { StaffsViewComponent } from './views/staffs-view.component';
import { StaffShowComponent } from './show/staff-show.component';

// Utilities

const routes: Routes = [
  {
    path: '',
    canActivateChild: [AuthGuard],
    component: StaffsComponent,
    children: [
        {
          path: '',
          component: StaffsViewComponent,
        },
        {
          path: 'create',
          component: StaffCreateComponent,
        },
        {
          path: ':id',
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
