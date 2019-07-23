import { messages } from './../features/extra-components/chat/messages';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { NgxAuthRoutingModule } from './auth-routing.module';
import {
  NbAuthModule,
  NbPasswordAuthStrategy,
  NbAuthJWTToken,
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
            token: {
              class: NbAuthJWTToken,
              getter: (
                module: string,
                res: HttpResponse<Object>, options: NbPasswordAuthStrategyOptions ) => res.headers.get('Authorization'),
            },
            errors: {
              getter: (
                module: string,
                res: HttpResponse<Object>, options: NbPasswordAuthStrategyOptions ) => {
                  const resp: any =  res.body;
                  let message = 'Sorry, some thing went wrong with error code of : ' + resp.status;
                  if (!resp.success && resp.message) {
                    message = resp.message;
                  }
                  return message;
                },
            },
            messages: {
              getter: (
                module: string,
                res: HttpResponse<Object>, options: NbPasswordAuthStrategyOptions ) => {
                  const resp: any =  res.body;
                  let message = 'No message to show';
                  if (resp.success && resp.message) {
                    message = resp.message;
                  }
                  return message;
                },
            },
            baseEndpoint: '/api/auth',
            login: {
              // ...
              endpoint: '/login',
              redirect: {
                success: '/dashboard',
                failure: null,
              },
            },
            register: {
              // ...
              endpoint: '/register',
            },
            logout: {
              endpoint: '/sign-out',
              method: 'POST',
              redirect: {
                success: '/account/login',
                failure: null,
              },
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
            redirectDelay:0,
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
            redirectDelay:0,
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
