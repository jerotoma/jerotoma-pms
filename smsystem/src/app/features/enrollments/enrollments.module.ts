import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';

import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';

import {
  NbActionsModule,
  NbButtonModule,
  NbDialogModule,
  NbWindowModule,
  NbCardModule,
  NbInputModule,
  NbIconModule,
  NbAlertModule,
} from '@nebular/theme';

import { EnrollmentsRoutingModule } from './enrollments-routing.module';
import { EnrollmentsComponent } from './enrollments.component';


const COMPONENTS = [
  EnrollmentsComponent,

];

const ENTRY_COMPONENTS = [

];

const MODULES = [
  SharedModule,
  NbActionsModule,
  MatTableModule,
  MatPaginatorModule,
  MatIconModule,
  MatMenuModule,
  NbButtonModule,
  NbWindowModule,
  NbCardModule,
  NbInputModule,
  NbIconModule,
  NbAlertModule,
  NbDialogModule.forRoot({
    closeOnBackdropClick: false,
    hasScroll: false,
    dialogClass: 'global-dialog-container',
  }),
  EnrollmentsRoutingModule,
];

const SERVICES = [
];


@NgModule({
imports: [
  ...MODULES,
],
declarations: [
  ...COMPONENTS,
],
providers: [
 ...SERVICES,
],
entryComponents: [
  ...ENTRY_COMPONENTS,
],
})
export class EnrollmentsModule { }
