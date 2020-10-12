import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { StudentCourseEnrollmentsComponent } from './student-academic-level-enrollments.component';
import { StudentAcademicLevelClassesEnrollmentsViewComponent } from './student-academic-level-classes-enrollments-view/student-academic-level-classes-enrollments-view.component'
import { StudentAcademicLevelClassesEnrollmentShowComponent } from './student-academic-level-classes-enrollment-show/student-academic-level-classes-enrollment-show.component';

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
      component: StudentAcademicLevelClassesEnrollmentsViewComponent,
    },
    {
      path: ':id',
      data: { roles: ALL_ROLES },
      canActivateChild: [AuthGuard],
      component: StudentAcademicLevelClassesEnrollmentShowComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class StudentAcademicLevelEnrollmentsRoutingModule {
}
