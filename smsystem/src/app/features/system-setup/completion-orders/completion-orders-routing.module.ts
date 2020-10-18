import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {CompletionOrdersComponent } from './completion-orders.component';
import { CompletionOrdersViewComponent } from './completion-order-view/completion-orders-view.component';


const routes: Routes = [{
  path: '',
  component: CompletionOrdersComponent,
  children: [
    {
      path: '',
      component: CompletionOrdersViewComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CompletionOrdersRoutingModule {
}
