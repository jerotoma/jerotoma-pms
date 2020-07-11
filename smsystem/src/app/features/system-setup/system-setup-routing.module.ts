import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { AuthGuard } from 'app/services/guards/auth-guard.service';
import { SystemSetupComponent } from './system-setup.component';
import { ADMINS_TEACHER_AND_EXECUTIVES_ROLES } from 'app/models';
import { NotFoundComponent } from 'app/features/miscellaneous/not-found/not-found.component';

const routes: Routes = [{
  path: '',
  canActivateChild: [AuthGuard],
  component: SystemSetupComponent,
  children: [
    {
      path: 'appearances',
      data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES},
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./appearances/appearances.module')
        .then(m => m.AppearancesModule),
    },
    {
      path: 'positions',
      data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES},
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./positions/positions.module')
        .then(m => m.PositionsModule),
    },
    {
      path: 'rooms',
      data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES},
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./rooms/rooms.module')
        .then(m => m.RoomsModule),
    },
    {
      path: 'academic-years',
      data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES},
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./academic-years/academic-years.module')
        .then(m => m.AcademicYearsModule),
    },
    {
      path: 'courses',
      data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES},
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./courses/courses.module')
        .then(m => m.CoursesModule),
    },
    {
      path: 'departments',
      data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES},
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./departments/departments.module')
        .then(m => m.DepartmentsModule),
    },
    {
      path: 'academic-disciplines',
      data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES},
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./academic-disciplines/academic-disciplines.module')
        .then(m => m.AcademicDisciplinesModule),
    },
    {
      path: 'academic-levels',
      data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES},
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./academic-levels/academic-levels.module')
        .then(m => m.AcademicLevelsModule),
    },
    {
      path: 'programs',
      data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES},
      canActivateChild: [AuthGuard],
      loadChildren: () => import('./programs/programs.module')
        .then(m => m.ProgramsModule),
    },
    {
      path: '',
      data: { roles: ADMINS_TEACHER_AND_EXECUTIVES_ROLES},
      canActivateChild: [AuthGuard],
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
