import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AcademicDisciplinesComponent } from './academic-disciplines.component';
import { AcademicDisciplinesViewComponent } from './discipline-view/academic-disciplines-view.component';


const routes: Routes = [{
  path: '',
  component: AcademicDisciplinesComponent,
  children: [
    {
      path: '',
      component: AcademicDisciplinesViewComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AcademicDisciplinesRoutingModule {
}
