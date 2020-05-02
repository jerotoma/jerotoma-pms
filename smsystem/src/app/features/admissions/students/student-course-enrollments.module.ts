import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';

import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import {MatMenuModule} from '@angular/material/menu';

import {
  NbActionsModule,
  NbButtonModule,
  NbSpinnerModule,
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
  NbTreeGridModule,
  NbTabsetModule,
  NbUserModule,
  NbCheckboxModule,
} from '@nebular/theme';

import { StudentCourseEnrollmentsRoutingModule } from './student-course-enrollments-routing.module';
import { StudentCourseEnrollmentsComponent } from './student-course-enrollments.component';
import { StudentCourseEnrollmentsViewComponent } from './student-course-enrollments-view/student-course-enrollments-view.component';
import { StudentCourseEnrollmentCreateComponent } from './student-course-enrollment-create/student-course-enrollment-create.component';
import { StudentCourseEnrollmentEditComponent } from './student-course-enrollment-edit/student-course-enrollment-edit.component';
import { StudentCourseEnrollmentShowComponent } from './student-course-enrollment-show/student-course-enrollment-show.component';

const COMPONENTS = [
  StudentCourseEnrollmentsComponent,
  StudentCourseEnrollmentsViewComponent,
  StudentCourseEnrollmentCreateComponent,
  StudentCourseEnrollmentEditComponent,
  StudentCourseEnrollmentShowComponent,
];

const ENTRY_COMPONENTS = [
  StudentCourseEnrollmentCreateComponent,
  StudentCourseEnrollmentEditComponent,

];

const MODULES = [
  SharedModule,
  MatTableModule,
  MatPaginatorModule,
  MatIconModule,
  MatListModule,
  NbActionsModule,
  NbSpinnerModule,
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
  NbTreeGridModule,
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
