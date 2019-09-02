import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ClassRoomsComponent } from './class-rooms.component';
import { ClassRoomsViewComponent } from './class-rooms-view/class-rooms-view.component';


const routes: Routes = [{
  path: '',
  component: ClassRoomsComponent,
  children: [
    {
      path: '',
      component: ClassRoomsViewComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ClassRoomsRoutingModule {
}
