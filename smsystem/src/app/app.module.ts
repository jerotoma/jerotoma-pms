import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {  MatDialogModule, MatDialog } from '@angular/material/dialog';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LayoutModule } from 'app/layouts/layout.module';
import { AppRoutingModule } from './app-routing.module';

import { AppAuthModule } from './auth/auth.module';

import 'hammerjs';
import 'mousetrap';
import { GalleryModule } from '@ks89/angular-modal-gallery';

import { JwtHelperService } from '@auth0/angular-jwt';
import { SharedCommonModule } from 'app/shared/common';
import { CoreModule } from 'app/core/core.module';
import { AppComponent } from './app.component';

import { NbEvaIconsModule } from '@nebular/eva-icons';
import {
  AuthInterceptor,
  HttpResponseErrorInterceptor,
  ModalService,
  AnalyticsService,
} from './services';

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
  ModalService,
  MatDialog,
  AnalyticsService,
];

@NgModule({
  declarations: [AppComponent],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    SharedCommonModule,
    AppAuthModule,
    NbEvaIconsModule,
    NbActionsModule,
    MatDialogModule,
    NbSidebarModule.forRoot(),
    NbMenuModule.forRoot(),
    NbDatepickerModule.forRoot(),
    NbDialogModule.forRoot({
      closeOnBackdropClick: false,
      hasScroll: false,
      dialogClass: 'global-dialog-container',
    }),
    NbWindowModule.forRoot(),
    NbToastrModule.forRoot(),
    NbChatModule.forRoot({
      messageGoogleMapKey: 'AIzaSyA_wNuCzia92MAmdLRzmqitRGvCF7wCZPY',
    }),
    CoreModule.forRoot(),
    LayoutModule.forRoot(),
    GalleryModule.forRoot(),
    AppRoutingModule,
  ],
  providers: [
    ...SERVICES,
    { provide: HTTP_INTERCEPTORS, useClass: HttpResponseErrorInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
