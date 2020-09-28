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

import { StudentAcademicLevelEnrollmentsRoutingModule } from './student-academic-level-enrollments-routing.module';
import { StudentCourseEnrollmentsComponent } from './student-academic-level-enrollments.component';
import { StudentAcademicLevelEnrollmentsViewComponent } from './student-academic-level-enrollments-view/student-academic-level-enrollments-view.component';
import { StudentCourseEnrollmentCreateComponent } from './student-academic-level-enrollment-create/student-academic-level-enrollment-create.component';
import { StudentAcademicLevelEnrollmentEditComponent } from './student-academic-level-enrollment-edit/student-academic-level-enrollment-edit.component';
import { StudentAcademicLevelEnrollmentShowComponent } from './student-academic-level-enrollment-show/student-academic-level-enrollment-show.component';

const COMPONENTS = [
  StudentCourseEnrollmentsComponent,
  StudentAcademicLevelEnrollmentsViewComponent,
  StudentCourseEnrollmentCreateComponent,
  StudentAcademicLevelEnrollmentEditComponent,
  StudentAcademicLevelEnrollmentShowComponent,
];

const ENTRY_COMPONENTS = [
  StudentCourseEnrollmentCreateComponent,
  StudentAcademicLevelEnrollmentEditComponent,

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
  NbDialogModule.forRoot({
    closeOnBackdropClick: false,
    hasScroll: false,
    dialogClass: 'global-dialog-container',
  }),
  StudentAcademicLevelEnrollmentsRoutingModule,
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
export class StudentAcademicLevelEnrollmentsModule { }
