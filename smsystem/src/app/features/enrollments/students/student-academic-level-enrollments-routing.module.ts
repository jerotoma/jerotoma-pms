import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { StudentCourseEnrollmentsComponent } from './student-academic-level-enrollments.component';
import { StudentAcademicLevelEnrollmentsViewComponent } from './student-academic-level-enrollments-view/student-academic-level-enrollments-view.component';
import { StudentAcademicLevelEnrollmentShowComponent } from './student-academic-level-enrollment-show/student-academic-level-enrollment-show.component';

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
      component: StudentAcademicLevelEnrollmentsViewComponent,
    },
    {
      path: ':id',
      data: { roles: ALL_ROLES },
      canActivateChild: [AuthGuard],
      component: StudentAcademicLevelEnrollmentShowComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class StudentAcademicLevelEnrollmentsRoutingModule {
}
