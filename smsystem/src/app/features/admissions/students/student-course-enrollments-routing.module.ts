import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { StudentCourseEnrollmentsComponent } from './student-course-enrollments.component';
import { StudentCourseEnrollmentsViewComponent } from './student-course-enrollments-view/student-course-enrollments-view.component';
import { StudentCourseEnrollmentShowComponent } from './student-course-enrollment-show/student-course-enrollment-show.component';

import { AuthGuard } from 'app/services';
import { ALL_ROLES } from 'app/models';

const routes: Routes = [{
  path: '',
  data: { roles: ALL_ROLES },
  canActivateChild: [AuthGuard],
  component: StudentCourseEnrollmentsComponent,
  children: [
    {
      path: '',
      data: { roles: ALL_ROLES },
      canActivateChild: [AuthGuard],
      component: StudentCourseEnrollmentsViewComponent,
    },
    {
      path: ':id',
      data: { roles: ALL_ROLES },
      canActivateChild: [AuthGuard],
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
