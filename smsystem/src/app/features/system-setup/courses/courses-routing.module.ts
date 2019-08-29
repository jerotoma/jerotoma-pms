import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {CoursesComponent } from './courses.component';
import { CoursesViewComponent } from './course-view/courses-view.component';


const routes: Routes = [{
  path: '',
  component: CoursesComponent,
  children: [
    {
      path: '',
      component: CoursesViewComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CoursesRoutingModule {
}
