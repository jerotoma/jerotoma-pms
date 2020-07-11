import { ExtraOptions, RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { ALL_ROLES } from 'app/models';
import { AuthGuard } from 'app/services';
const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  {
    path: 'dashboard',
    canActivate: [AuthGuard], // here we tell Angular to check the access with our AuthGuard
    data: { roles: ALL_ROLES },
    loadChildren: () => import('./features/features.module')
      .then(m => m.FeaturesModule),
  },
  {
    path: 'account',
    loadChildren: () => import('./auth/auth.module').then(m => m.AppAuthModule),

  },
  { path: '**', redirectTo: '' },
];

const config: ExtraOptions = {
  useHash: false,
};

@NgModule({
  imports: [RouterModule.forRoot(routes, config)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
