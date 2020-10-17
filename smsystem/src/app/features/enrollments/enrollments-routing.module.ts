import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from 'app/services';
import { ADMINS_TEACHER_AND_EXECUTIVES_ROLES } from 'app/models';
import { EnrollmentsComponent } from './enrollments.component';

const routes: Routes = [
  {
    path: '',
    data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES },
    canActivateChild: [AuthGuard],
    component: EnrollmentsComponent,
    children: [
      {
        path: 'student-academic-levels',
        data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES },
        canActivateChild: [AuthGuard],
        loadChildren: () => import('./students/student-academic-level-enrollments.module')
          .then(m => m.StudentAcademicLevelEnrollmentsModule),
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
export class EnrollmentsRoutingModule {
}

