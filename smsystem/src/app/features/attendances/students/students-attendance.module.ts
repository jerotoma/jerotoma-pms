import { NgModule } from '@angular/core';
import { StudentsAttendanceRoutingModule } from './students-attendance-routing.module';
import { SharedModule } from 'app/shared/shared.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatTableModule } from '@angular/material/table';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatFormFieldModule} from '@angular/material/form-field';
import { MatNativeDateModule} from '@angular/material/core';
import {MatInputModule} from '@angular/material/input';

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
  NbTabsetModule,
  NbListModule,
  NbRadioModule,
} from '@nebular/theme';

import { StudentsAttendanceComponent } from './students-attendance.component';
import { RecordStudentAttendenceComponent } from './record-student-attendance/record-student-attendance.component';



const COMPONENTS = [
  StudentsAttendanceComponent,
  RecordStudentAttendenceComponent,
];

const ENTRY_COMPONENTS = [

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
  NbRadioModule,
  NbSelectModule,
  NbTabsetModule,
  NbCheckboxModule,
  NbListModule,
  NbDatepickerModule,
  NbIconModule,
  NbAlertModule,
  MatTableModule,
  MatPaginatorModule,
  MatIconModule,
  MatMenuModule,
  MatNativeDateModule,
  MatDatepickerModule,
  MatInputModule,
  MatFormFieldModule,
  NbDialogModule.forRoot({
    closeOnBackdropClick: false,
    hasScroll: false,
    dialogClass: 'global-dialog-container',
  }),
];

const SERVICES = [
  MatNativeDateModule,
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
