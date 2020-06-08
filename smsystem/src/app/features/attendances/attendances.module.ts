import { NgModule } from '@angular/core';
import { AttendancesRoutingModule } from './attendances-routing.module';
import { SharedModule } from 'app/shared/shared.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {MatTableModule, MatMenuModule, MatIconModule, MatPaginatorModule} from '@angular/material';

import {
  NbButtonModule,
  NbSpinnerModule,
  NbDialogModule,
  NbWindowModule,
  NbCardModule,
  NbInputModule,
  NbIconModule,
  NbAlertModule,
  NbSelectModule,
  NbCheckboxModule,
  NbListModule,
} from '@nebular/theme';

import { AttendancesComponent } from './attendances.component';

const COMPONENTS = [
  AttendancesComponent,
];

const ENTRY_COMPONENTS = [

];

const MODULES = [
  SharedModule,
  AttendancesRoutingModule,
  NgbModule,
  NbButtonModule,
  NbSpinnerModule,
  NbWindowModule,
  NbCardModule,
  NbInputModule,
  NbSelectModule,
  NbCheckboxModule,
  NbListModule,
  NbIconModule,
  NbAlertModule,
  MatTableModule,
  MatPaginatorModule,
  MatIconModule,
  MatMenuModule,
  NbDialogModule.forRoot({
    closeOnBackdropClick: false,
    hasScroll: false,
  }),
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
export class AttendancesModule { }
