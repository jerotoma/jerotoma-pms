import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ProgramsComponent } from './programs.component';
import {ProgramListComponent } from './program-list/program-list.component';
import {ProgramShowComponent } from './program-show/program-show.component';

const routes: Routes = [{
  path: '',
  component: ProgramsComponent,
  children: [
    {
      path: '',
      component: ProgramListComponent,
    },
    {
      path: ':id',
      component: ProgramShowComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProgramsRoutingModule { }
