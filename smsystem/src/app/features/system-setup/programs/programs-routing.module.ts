import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';



import {ProgramsComponent } from './programs.component';
import {ProgramListComponent } from './program-list/program-list.component';

const routes: Routes = [{
  path: '',
  component: ProgramsComponent,
  children: [
    {
      path: '',
      component: ProgramListComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProgramsRoutingModule { }
