import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import {
  MatButtonModule,
  MatTableModule,
  MatPaginatorModule,
  MatIconModule,
  MatSelectModule,
  MatMenuModule,
  MatDialog,
  MatSnackBarModule,
  MAT_SNACK_BAR_DEFAULT_OPTIONS,
} from '@angular/material';

import { FileUploadModule } from 'ng2-file-upload';
import { ThemeModule } from 'app/@theme/theme.module';
import { NbMomentDateModule } from '@nebular/moment';
import {
  NbAccordionModule,
  NbButtonModule,
  NbDialogModule,
  NbWindowModule,
  NbCardModule,
  NbListModule,
  NbSelectModule,
  NbRadioModule,
  NbDatepickerModule,
  NbRouteTabsetModule,
  NbStepperModule,
  NbTabsetModule,
  NbActionsModule,
  NbInputModule,
  NbIconModule,
  NbUserModule,
  NbCheckboxModule,
  NbAlertModule,
} from '@nebular/theme';

import { SharedCommonModule } from './common';
import {
  UploadsComponent,
  UserTableComponent,
  UserDetailsComponent,
  MyCourseComponent,
  AddressComponent,
  DeleteModalComponent,
  UserLoginInputComponent,
  SearchUserComponent,
  UserDeleteComponent,
  TimepickerComponent,
  CurrentAcademicYearComponent,
} from 'app/shared';

const COMPONENTS = [
  AddressComponent,
  DeleteModalComponent,
  UserDeleteComponent,
  MyCourseComponent,
  UserTableComponent,
  UserDetailsComponent,
  UploadsComponent,
  UserLoginInputComponent,
  SearchUserComponent,
  TimepickerComponent,
  CurrentAcademicYearComponent,
];

const ENTRY_COMPONENTS = [
  UserDeleteComponent,
  DeleteModalComponent,
  UploadsComponent,
  SearchUserComponent,
  UserLoginInputComponent,
  MyCourseComponent,
  TimepickerComponent,
  CurrentAcademicYearComponent,
];

const MODULES = [
  RouterModule ,
  FormsModule,
  ReactiveFormsModule,
  FileUploadModule,
  ThemeModule,
  NgbModule,
  NbMomentDateModule,
  NbTabsetModule,
  NbRadioModule,
  NbDatepickerModule,
  NbAlertModule,
  NbRouteTabsetModule,
  NbStepperModule,
  NbCardModule,
  NbCheckboxModule,
  NbSelectModule,
  NbInputModule,
  NbButtonModule,
  NbListModule,
  NbAccordionModule,
  NbUserModule,
  NbActionsModule,
  NbIconModule,
  MatTableModule,
  MatPaginatorModule,
  MatIconModule,
  MatMenuModule,
  MatSelectModule,
  MatSnackBarModule,
  MatButtonModule,
  SharedCommonModule,
  NbDialogModule.forRoot({
    closeOnBackdropClick: false,
    hasScroll: false,
  }),
  NbWindowModule.forRoot(),
];

const EXPORT_MODULES = [
  FormsModule,
  ReactiveFormsModule,
  CommonModule,
  RouterModule,
];

const SERVICES = [
  MatDialog,
];


@NgModule({
  imports: [
    ...MODULES,
  ],
  declarations: [
    ...COMPONENTS,
    MyCourseComponent,
  ],
  exports: [
    ...EXPORT_MODULES,
    ...COMPONENTS,
  ],
  providers: [
   ...SERVICES,
   {
     provide: MAT_SNACK_BAR_DEFAULT_OPTIONS,
     useValue: {
       duration: 6500,
      },
    },
  ],
  entryComponents: [
    ...ENTRY_COMPONENTS,
  ],
})
export class SharedModule { }
