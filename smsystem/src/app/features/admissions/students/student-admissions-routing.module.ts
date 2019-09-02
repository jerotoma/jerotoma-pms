import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { StudentAdmissionsComponent } from './student-admissions.component';
import { StudentAdmissionsViewComponent } from './student-admissions-view/student-admissions-view.component';
import { StudentAdmissionCreateComponent } from './student-admission-create/student-admission-create.component';


const routes: Routes = [{
  path: '',
  component: StudentAdmissionsComponent,
  children: [
    {
      path: '',
      component: StudentAdmissionsViewComponent,
    },
    {
      path: 'create',
      component: StudentAdmissionCreateComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class StudentAdmissionsRoutingModule {
}
