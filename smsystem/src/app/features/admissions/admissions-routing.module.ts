import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AdmissionsComponent } from './admissions.component';

const routes: Routes = [
  {
    path: '',
    component: AdmissionsComponent,
    children: [
      {
        path: 'teachers',
        loadChildren: () => import('./teachers/teacher-admissions.module')
          .then(m => m.TeacherAdmissionsModule),
      },
      {
        path: 'students',
        loadChildren: () => import('./students/student-admissions.module')
          .then(m => m.StudentAdmissionsModule),
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

