import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { NgxAuthRoutingModule } from './auth-routing.module';
import { NbAuthModule, NbPasswordAuthStrategy } from '@nebular/auth';
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
    FormsModule,
    RouterModule,
    NbAlertModule,
    NbInputModule,
    NbButtonModule,
    NbCheckboxModule,
    NbIconModule,
    NbCardModule,
    NgxAuthRoutingModule,
    NbAuthModule.forRoot({
        strategies: [
          NbPasswordAuthStrategy.setup({
            name: 'email',
            baseEndpoint: '/api/auth',
             login: {
               // ...
               endpoint: '/login',
             },
             register: {
               // ...
               endpoint: '/register',
             },
             logout: {
              endpoint: '/sign-out',
            },
            requestPass: {
              endpoint: '/request-pass',
            },
            resetPass: {
              endpoint: '/reset-pass',
            },
          }),
        ],
        forms: {
          login: {
            redirectDelay: 0,
            showMessages: {
              success: true,
            },
          },
          register: {
            redirectDelay: 0,
            showMessages: {
              success: true,
            },
          },
          requestPassword: {
            redirectDelay: 0,
            showMessages: {
              success: true,
            },
          },
          resetPassword: {
            redirectDelay: 0,
            showMessages: {
              success: true,
            },
          },
          logout: {
            redirectDelay: 0,
          },
        },
      }),
    NbLayoutModule,
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
export class NgxAuthModule {
}
