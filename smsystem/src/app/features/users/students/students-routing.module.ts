// Modules
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Service
import { AuthGuard } from 'app/services';
import { ALL_ROLES } from 'app/models';

// Components
import { StudentsComponent } from './students.component';
import { StudentsViewComponent } from './views/students-view.component';
import { StudentShowComponent } from './show/student-show.component';

// Utilities

const routes: Routes = [
  {
    path: '',
    data: { roles: ALL_ROLES },
    canActivateChild: [AuthGuard],
    component: StudentsComponent,
    children: [
        {
          path: '',
          data: { roles: ALL_ROLES },
          canActivateChild: [AuthGuard],
          component: StudentsViewComponent,
        },
        {
          path: ':id',
          data: { roles: ALL_ROLES },
          canActivateChild: [AuthGuard],
          component: StudentShowComponent,
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
