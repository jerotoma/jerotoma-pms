import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CoreModule } from './@core/core.module';
import { ThemeModule } from './@theme/theme.module';
import { JwtHelperService } from '@auth0/angular-jwt';

import {  MatDialogModule, MatDialog } from '@angular/material';

import { SharedCommonModule } from 'app/shared/common';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { AuthInterceptor, HttpResponseErrorInterceptor, ErrorDialogService } from './services';
import { AppAuthModule } from './auth/auth.module';
import { NbEvaIconsModule } from '@nebular/eva-icons';
import {
  NbAuthService,
  NbTokenService,
} from '@nebular/auth';
import {
  NbChatModule,
  NbDatepickerModule,
  NbDialogModule,
  NbMenuModule,
  NbSidebarModule,
  NbToastrModule,
  NbActionsModule,
  NbWindowModule,
} from '@nebular/theme';



const SERVICES = [
  JwtHelperService,
  NbAuthService,
  NbTokenService,
  ErrorDialogService,
  MatDialog,
  {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true,
  },
  {
    provide: HTTP_INTERCEPTORS,
    useClass: HttpResponseErrorInterceptor,
    multi: true,
  },
];

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    AppAuthModule,
    NbEvaIconsModule,
    NbActionsModule,
    MatDialogModule,
    SharedCommonModule,
    ThemeModule.forRoot(),
    NbSidebarModule.forRoot(),
    NbMenuModule.forRoot(),
    NbDatepickerModule.forRoot(),
    NbDialogModule.forRoot(),
    NbWindowModule.forRoot(),
    NbToastrModule.forRoot(),
    NbChatModule.forRoot({
      messageGoogleMapKey: 'AIzaSyA_wNuCzia92MAmdLRzmqitRGvCF7wCZPY',
    }),
    CoreModule.forRoot(),
  ],
  providers: [
    ...SERVICES,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
