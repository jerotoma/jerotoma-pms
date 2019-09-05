import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { StudentCourseEnrollmentsComponent } from './student-course-enrollments.component';
import { StudentCourseEnrollmentsViewComponent } from './student-course-enrollments-view/student-course-enrollments-view.component';

const routes: Routes = [{
  path: '',
  component: StudentCourseEnrollmentsComponent,
  children: [
    {
      path: '',
      component: StudentCourseEnrollmentsViewComponent,
    }
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class StudentCourseEnrollmentsRoutingModule {
}
