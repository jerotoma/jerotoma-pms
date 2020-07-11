import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from 'app/services';
import { ADMINS_TEACHER_AND_EXECUTIVES_ROLES } from 'app/models';
import { AdmissionsComponent } from './admissions.component';

const routes: Routes = [
  {
    path: '',
    data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES },
    canActivateChild: [AuthGuard],
    component: AdmissionsComponent,
    children: [
      {
        path: 'students',
        data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES },
        canActivateChild: [AuthGuard],
        loadChildren: () => import('./students/student-course-enrollments.module')
          .then(m => m.StudentCourseEnrollmentsModule),
      },
      {
        path: 'classes',
        data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES },
        canActivateChild: [AuthGuard],
        loadChildren: () => import('./classes/classes.module')
          .then(m => m.ClassesModule),
      },
    ],
  },
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
  ],
  exports: [
    RouterModule,
  ],
})
export class AdmissionsRoutingModule {
}

