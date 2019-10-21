import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AppearancesComponent } from './appearances.component';
import { AppearancesViewComponent } from './appearances-view/appearances-view.component';


const routes: Routes = [{
  path: '',
  component: AppearancesComponent,
  children: [
    {
      path: '',
      component: AppearancesViewComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AppearancesRoutingModule {
}
