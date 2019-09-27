// Modules
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Service
import { AuthGuard } from 'app/services/guards/auth-guard.service';

// Components
import { ParentsComponent } from './parents.component';
import { ParentCreateComponent } from './create/parent-create.component';
import { ParentShowComponent } from './show/parent-show.component';
import { ParentsViewComponent } from './views/parents-view.component';

// Utilities

const routes: Routes = [
  {
    path: '',
    canActivateChild: [AuthGuard],
    component: ParentsComponent,
    children: [
        {
          path: '',
          component: ParentsViewComponent,
        },
        {
          path: ':id',
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
