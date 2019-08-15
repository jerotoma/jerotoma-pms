import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { AuthGuard } from 'app/services/guards/auth-guard.service';
import { SystemSetupComponent } from './system-setup.component';
import { NotFoundComponent } from 'app/features/miscellaneous/not-found/not-found.component';

const routes: Routes = [{
  path: '',
  canActivateChild: [AuthGuard],
  component: SystemSetupComponent,
  children: [
    {
      path: 'positions',
      loadChildren: () => import('./positions/positions.module')
        .then(m => m.PositionsModule),
    },
    {
      path: 'academic-disciplines',
      loadChildren: () => import('./academic-disciplines/academic-disciplines.module')
        .then(m => m.AcademicDisciplinesModule),
    },
    {
      path: '',
      redirectTo: 'system-setup',
      pathMatch: 'full',
    },
    {
      path: '**',
      component: NotFoundComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SystemSetupRoutingModule {
}
