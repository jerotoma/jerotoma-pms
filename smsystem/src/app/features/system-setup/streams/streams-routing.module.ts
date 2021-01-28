import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { StreamsComponent } from './streams.component';
import { StreamViewComponent } from './view/stream-view.component';


const routes: Routes = [{
  path: '',
  component: StreamsComponent,
  children: [
    {
      path: '',
      component: StreamViewComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class StreamsRoutingModule {
}
