import { NgModule } from '@angular/core';
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

import { StaffsAttendanceRoutingModule } from './staffs-attendance-routing.module';
import { StaffsAttendanceComponent } from './staffs-attendance.component';



const COMPONENTS = [
  StaffsAttendanceComponent,
];

const ENTRY_COMPONENTS = [

];

const MODULES = [
  SharedModule,
  StaffsAttendanceRoutingModule,
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
export class StaffsAttendanceModule { }
