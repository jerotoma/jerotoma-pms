// Modules
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Service
import { AuthGuard } from 'app/services';
import { ALL_ROLES } from 'app/models';

// Components
import { TeachersComponent } from './teachers.component';
import { TeacherCreateComponent } from './create/teacher-create.component';
import { TeachersViewComponent } from './views/teachers-view.component';
import { TeacherShowComponent } from './show/teacher-show.component';



// Utilities

const routes: Routes = [
  {
    path: '',
    data: { roles: ALL_ROLES },
    canActivateChild: [AuthGuard],
    component: TeachersComponent ,
    children: [
        {
          path: '',
          data: { roles: ALL_ROLES },
          canActivateChild: [AuthGuard],
          component: TeachersViewComponent ,
        },
        {
          path: ':id',
          data: { roles: ALL_ROLES },
          canActivateChild: [AuthGuard],
          component: TeacherShowComponent ,
        },
        {
          path: 'create',
          data: { roles: ALL_ROLES },
          canActivateChild: [AuthGuard],
          component: TeacherCreateComponent ,
        },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TeachersRoutingModule {
}
