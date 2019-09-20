import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'app/shared/shared.module';
import { RouterModule } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { AppAuthRoutingModule } from './auth-routing.module';
import {
  NbAuthModule,
  NbPasswordAuthStrategy,
  NbAuthJWTToken,
  NbAuthService,
  NbTokenService,
  NbPasswordAuthStrategyOptions,
 } from '@nebular/auth';
import {
  NbAlertModule,
  NbButtonModule,
  NbCheckboxModule,
  NbInputModule,
  NbIconModule,
  NbCardModule,
  NbLayoutModule,
} from '@nebular/theme';

import { AuthComponent } from './auth.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { LogoutComponent } from './logout/logout.component';
import { RequestPasswordComponent } from './request-password/request-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';


@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    RouterModule,
    AppAuthRoutingModule,
    NbAlertModule,
    NbInputModule,
    NbButtonModule,
    NbCheckboxModule,
    NbIconModule,
    NbCardModule,
    NbLayoutModule,
    NbAuthModule.forRoot(),
  ],
  declarations: [
    // ... here goes our new components
    AuthComponent,
    LoginComponent,
    RegisterComponent,
    LogoutComponent,
    RequestPasswordComponent,
    ResetPasswordComponent,
  ],
})
export class AppAuthModule {
}
