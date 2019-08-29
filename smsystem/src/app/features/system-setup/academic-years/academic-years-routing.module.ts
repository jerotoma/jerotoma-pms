import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AcademicYearsComponent } from './academic-years.component';
import { AcademicYearsViewComponent } from './academic-years-view/academic-years-view.component';


const routes: Routes = [{
  path: '',
  component: AcademicYearsComponent,
  children: [
    {
      path: '',
      component: AcademicYearsViewComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AcademicYearsRoutingModule {
}
