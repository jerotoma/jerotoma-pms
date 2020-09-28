import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ClassesComponent } from './classes.component';
import { ClassesViewComponent } from './classes-view/classes-view.component';
import { AuthGuard } from 'app/services';
import { ALL_ROLES } from 'app/models';


const routes: Routes = [{
  path: '',
  data: { roles: ALL_ROLES },
  canActivateChild: [AuthGuard],
  component: ClassesComponent ,
  children: [
    {
      path: '',
      data: { roles: ALL_ROLES },
      canActivateChild: [AuthGuard],
      component: ClassesViewComponent,
    },
    {
      path: ':id',
      data: { roles: ALL_ROLES },
      canActivateChild: [AuthGuard],
      component: ClassesViewComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ClassesRoutingModule {
}
