import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PositionsComponent } from './positions.component';
import { PositionsViewComponent } from './positions-view/positions-view.component';


const routes: Routes = [{
  path: '',
  component: PositionsComponent,
  children: [
    {
      path: '',
      component: PositionsViewComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PositionsRoutingModule {
}
