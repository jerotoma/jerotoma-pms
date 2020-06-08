import { NgModule } from '@angular/core';
import { StudentsAttendanceRoutingModule } from './students-attendance-routing.module';
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
  NbDatepickerModule,
  NbListModule,
} from '@nebular/theme';

import { StudentsAttendanceComponent } from './students-attendance.component';
import { ClassAttendenceCreateComponent } from './class-attendance-create/class-attendance-create.component';



const COMPONENTS = [
  StudentsAttendanceComponent,
  ClassAttendenceCreateComponent,
];

const ENTRY_COMPONENTS = [
  ClassAttendenceCreateComponent,
];

const MODULES = [
  SharedModule,
  StudentsAttendanceRoutingModule,
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
export class StudentsAttendanceModule { }
