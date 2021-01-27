import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


import {AcademicLevelsComponent } from './academic-levels.component';
import {AcademicLevelListComponent } from './academic-level-list/academic-level-list.component';
import {AcademicLevelShowComponent } from './academic-level-show/academic-level-show.component';


const routes: Routes = [{
  path: '',
  component: AcademicLevelsComponent,
  children: [
    {
      path: '',
      component: AcademicLevelListComponent,
    },
    {
      path: ':id',
      component: AcademicLevelShowComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AcademicLevelsRoutingModule { }
