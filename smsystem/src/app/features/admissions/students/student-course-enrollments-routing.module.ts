import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { StudentCourseEnrollmentsComponent } from './student-course-enrollments.component';
import { StudentCourseEnrollmentsViewComponent } from './student-course-enrollments-view/student-course-enrollments-view.component';
import { StudentCourseEnrollmentShowComponent } from './student-course-enrollment-show/student-course-enrollment-show.component';

const routes: Routes = [{
  path: '',
  component: StudentCourseEnrollmentsComponent,
  children: [
    {
      path: '',
      component: StudentCourseEnrollmentsViewComponent,
    },
    {
      path: ':id',
      component: StudentCourseEnrollmentShowComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class StudentCourseEnrollmentsRoutingModule {
}
