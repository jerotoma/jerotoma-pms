import { NgModule } from '@angular/core';
import {SharedModule } from 'app/shared/shared.module';

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
  NbAccordionModule,
  NbListModule,
  NbSelectModule,
  NbRadioModule,
  NbDatepickerModule,
  NbRouteTabsetModule,
  NbStepperModule,
  NbTabsetModule,
  NbUserModule,
  NbCheckboxModule,
} from '@nebular/theme';

import { StudentAdmissionsRoutingModule } from './student-admissions-routing.module';
import { StudentAdmissionsComponent } from './student-admissions.component';
import { StudentAdmissionsViewComponent } from './student-admissions-view/student-admissions-view.component';
import { StudentAdmissionCreateComponent } from './student-admission-create/student-admission-create.component';


const COMPONENTS = [
  StudentAdmissionsComponent,
  StudentAdmissionsViewComponent,
  StudentAdmissionCreateComponent,
];

const ENTRY_COMPONENTS = [

];

const MODULES = [
  SharedModule,
  MatTableModule,
  MatPaginatorModule,
  MatIconModule,
  NbActionsModule,
  NbAccordionModule,
  NbListModule,
  NbSelectModule,
  NbRadioModule,
  NbDatepickerModule,
  NbRouteTabsetModule,
  NbStepperModule,
  NbTabsetModule,
  NbUserModule,
  NbCheckboxModule,
  MatMenuModule,
  NbButtonModule,
  NbWindowModule,
  NbCardModule,
  NbInputModule,
  NbIconModule,
  NbAlertModule,
  NbDialogModule.forRoot(),
  StudentAdmissionsRoutingModule,
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
export class StudentAdmissionsModule { }
