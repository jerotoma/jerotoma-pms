import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from 'app/services';
import { MyCoursesComponent } from './my-courses.component';
import { STUDENT_AND_TEACHER_ROLES } from 'app/models';

const routes: Routes = [
  {
    path: '',
    canActivateChild: [AuthGuard],
    data: { roles: STUDENT_AND_TEACHER_ROLES},
    component: MyCoursesComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MyCoursesRoutingModule { }
