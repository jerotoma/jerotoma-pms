import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AdmissionsComponent } from './admissions.component';

const routes: Routes = [
  {
    path: '',
    component: AdmissionsComponent,
    children: [
      {
        path: 'students',
        loadChildren: () => import('./students/student-course-enrollments.module')
          .then(m => m.StudentCourseEnrollmentsModule),
      },
      {
        path: 'classes',
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

