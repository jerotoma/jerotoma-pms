import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


import {AcademicLevelsComponent } from './academic-levels.component';
import {AcademicLevelListComponent } from './academic-level-list/academic-level-list.component';


const routes: Routes = [{
  path: '',
  component: AcademicLevelsComponent,
  children: [
    {
      path: '',
      component: AcademicLevelListComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AcademicLevelsRoutingModule { }
