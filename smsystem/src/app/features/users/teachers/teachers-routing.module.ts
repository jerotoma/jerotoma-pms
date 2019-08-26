// Modules
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Service
import { AuthGuard } from 'app/services/guards/auth-guard.service';

// Components
import { TeachersComponent } from './teachers.component';
import { TeacherCreateComponent } from './create/teacher-create.component';
import { TeachersViewComponent } from './views/teachers-view.component';
import { TeacherShowComponent } from './show/teacher-show.component';


// Utilities

const routes: Routes = [
  {
    path: '',
    canActivateChild: [AuthGuard],
    component: TeachersComponent ,
    children: [
        {
          path: '',
          component: TeachersViewComponent ,
        },
        {
          path: ':id',
          component: TeacherShowComponent ,
        },
        {
          path: 'create',
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
