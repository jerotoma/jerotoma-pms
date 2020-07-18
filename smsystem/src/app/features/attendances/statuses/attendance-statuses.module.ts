import { NgModule } from '@angular/core';
import { AttendanceStatusesRoutingModule } from './attendance-statuses-routing.module';
import { SharedModule } from 'app/shared/shared.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatTableModule } from '@angular/material/table';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';

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
  NbDatepickerModule,
  NbListModule,
} from '@nebular/theme';

import { AttendanceStatusesComponent } from './attendance-statuses.component';
import { AttendanceStatusesCreateComponent} from './attendance-statuses-create/attendance-statuses-create.component';



const COMPONENTS = [
  AttendanceStatusesComponent,
  AttendanceStatusesCreateComponent,
];

const ENTRY_COMPONENTS = [
  AttendanceStatusesCreateComponent,
];

const MODULES = [
  SharedModule,
  AttendanceStatusesRoutingModule,
  NgbModule,
  NbButtonModule,
  NbSpinnerModule,
  NbWindowModule,
  NbCardModule,
  NbInputModule,
  NbSelectModule,
  NbCheckboxModule,
  NbListModule,
  NbDatepickerModule,
  NbIconModule,
  NbAlertModule,
  MatTableModule,
  MatPaginatorModule,
  MatIconModule,
  MatMenuModule,
  NbDialogModule.forRoot({
    closeOnBackdropClick: false,
    hasScroll: false,
    dialogClass: 'global-dialog-container',
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
export class AttendanceStatusesModule { }
