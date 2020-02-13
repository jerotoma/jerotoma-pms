import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

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

import { SharedCommonModule, } from './common';
import {
  UploadsComponent,
  UserTableComponent,
  UserDetailsComponent,
  AddressComponent,
  DeleteModalComponent,
  UserLoginInputComponent,
  UserDeleteComponent,
} from 'app/shared';



const COMPONENTS = [
  AddressComponent,
  DeleteModalComponent,
  UserDeleteComponent,
  UserTableComponent,
  UserDetailsComponent,
  UploadsComponent,
  UserLoginInputComponent,
];

const ENTRY_COMPONENTS = [
  UserDeleteComponent,
  DeleteModalComponent,
  UploadsComponent,
  UserLoginInputComponent,
];

const MODULES = [
  RouterModule ,
  FormsModule,
  ReactiveFormsModule,
  FileUploadModule,
  ThemeModule,
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
    UserLoginInputComponent,
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
