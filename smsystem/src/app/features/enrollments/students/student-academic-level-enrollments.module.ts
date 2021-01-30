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
import { StudentAcademicLevelClassesEnrollmentsViewComponent } from './student-academic-level-classes-enrollments-view/student-academic-level-classes-enrollments-view.component';
import { StudentAcademicLevelClassesEnrollmentEditComponent } from './student-academic-level-classes-enrollment-edit/student-academic-level-classes-enrollment-edit.component';
import { StudentAcademicLevelClassesEnrollmentShowComponent } from './student-academic-level-classes-enrollment-show/student-academic-level-classes-enrollment-show.component';

const COMPONENTS = [
  StudentCourseEnrollmentsComponent,
  StudentAcademicLevelClassesEnrollmentsViewComponent,
  StudentAcademicLevelClassesEnrollmentEditComponent,
  StudentAcademicLevelClassesEnrollmentShowComponent,
];

const ENTRY_COMPONENTS = [
  StudentAcademicLevelClassesEnrollmentEditComponent,
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
