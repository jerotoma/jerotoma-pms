import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from 'app/services';
import { ResourcesComponent } from './resources.component';
import { ALL_ROLES } from 'app/models';

const routes: Routes = [
  {
    path: '',
    canActivateChild: [AuthGuard],
    data: { roles: ALL_ROLES},
    component: ResourcesComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ResourcesRoutingModule { }
