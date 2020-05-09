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
      path: 'appearances',
      loadChildren: () => import('./appearances/appearances.module')
        .then(m => m.AppearancesModule),
    },
    {
      path: 'positions',
      loadChildren: () => import('./positions/positions.module')
        .then(m => m.PositionsModule),
    },
    {
      path: 'rooms',
      loadChildren: () => import('./rooms/rooms.module')
        .then(m => m.RoomsModule),
    },
    {
      path: 'academic-years',
      loadChildren: () => import('./academic-years/academic-years.module')
        .then(m => m.AcademicYearsModule),
    },
    {
      path: 'courses',
      loadChildren: () => import('./courses/courses.module')
        .then(m => m.CoursesModule),
    },
    {
      path: 'departments',
      loadChildren: () => import('./departments/departments.module')
        .then(m => m.DepartmentsModule),
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
