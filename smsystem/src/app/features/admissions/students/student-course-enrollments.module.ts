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

import { StudentCourseEnrollmentsRoutingModule } from './student-course-enrollments-routing.module';
import { StudentCourseEnrollmentsComponent } from './student-course-enrollments.component';
import { StudentCourseEnrollmentsViewComponent } from './student-course-enrollments-view/student-course-enrollments-view.component';
import { StudentCourseEnrollmentCreateComponent } from './student-course-enrollment-create/student-course-enrollment-create.component';


const COMPONENTS = [
  StudentCourseEnrollmentsComponent,
  StudentCourseEnrollmentsViewComponent,
  StudentCourseEnrollmentCreateComponent,
];

const ENTRY_COMPONENTS = [
  StudentCourseEnrollmentCreateComponent,

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
  StudentCourseEnrollmentsRoutingModule,
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
export class StudentCourseEnrollmentsModule { }
