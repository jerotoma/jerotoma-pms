import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {ScoreStandingsComponent } from './score-standings.component';
import { ScoreStandingsViewComponent } from './score-standing-view/score-standings-view.component';


const routes: Routes = [{
  path: '',
  component: ScoreStandingsComponent,
  children: [
    {
      path: '',
      component: ScoreStandingsViewComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ScoreStandingsRoutingModule {
}
