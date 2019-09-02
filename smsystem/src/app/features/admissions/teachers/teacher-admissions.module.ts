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
  NbAlertModule,
} from '@nebular/theme';

import { TeacherAdmissionsRoutingModule } from './teacher-admissions-routing.module';
import { TeacherAdmissionsComponent } from './teacher-admissions.component';
import { TeacherAdmissionsViewComponent } from './teacher-admissions-view/teacher-admissions-views.component';


const COMPONENTS = [
  TeacherAdmissionsComponent,
  TeacherAdmissionsViewComponent,
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
  TeacherAdmissionsRoutingModule,
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
export class TeacherAdmissionsModule { }
