import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {DepartmentsComponent } from './departments.component';
import { DepartmentsViewComponent } from './department-view/departments-view.component';


const routes: Routes = [{
  path: '',
  component: DepartmentsComponent,
  children: [
    {
      path: '',
      component: DepartmentsViewComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DepartmentsRoutingModule {
}
