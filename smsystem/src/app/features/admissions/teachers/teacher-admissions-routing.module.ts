import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TeacherAdmissionsComponent } from './teacher-admissions.component';
import { TeacherAdmissionsViewComponent } from './teacher-admissions-view/teacher-admissions-views.component';


const routes: Routes = [{
  path: '',
  component: TeacherAdmissionsComponent,
  children: [
    {
      path: '',
      component: TeacherAdmissionsViewComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TeacherAdmissionsRoutingModule {
}
