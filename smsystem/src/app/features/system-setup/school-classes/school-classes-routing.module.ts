import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SchoolClassesComponent } from './school-classes.component';
import { SchoolClassesViewComponent } from './school-classes-view/school-classes-view.component';


const routes: Routes = [{
  path: '',
  component: SchoolClassesComponent,
  children: [
    {
      path: '',
      component: SchoolClassesViewComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SchoolClassesRoutingModule {
}
