import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RoomsComponent } from './rooms.component';
import { RoomsViewComponent } from './rooms-view/rooms-view.component';


const routes: Routes = [{
  path: '',
  component: RoomsComponent,
  children: [
    {
      path: '',
      component: RoomsViewComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RoomsRoutingModule {
}
