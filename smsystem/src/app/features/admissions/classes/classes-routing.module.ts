import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ClassesComponent } from './classes.component';
import { ClassesViewComponent } from './classes-view/classes-view.component';

const routes: Routes = [{
  path: '',
  component: ClassesComponent ,
  children: [
    {
      path: '',
      component: ClassesViewComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ClassesRoutingModule {
}
