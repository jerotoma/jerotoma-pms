import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatMenuModule} from '@angular/material/menu';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBarModule, MAT_SNACK_BAR_DEFAULT_OPTIONS } from '@angular/material/snack-bar';

import { FileUploadModule } from 'ng2-file-upload';
import { LayoutModule } from 'app/layouts/layout.module';
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
  NbSpinnerModule,
  NbProgressBarModule,
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
  TinyMCEComponent,
  CurrentAcademicYearComponent,
  LogoComponent,
  UploadAvatarDialogComponent,
  ProgressComponent,
  ParentCreateComponent,
  AddParentComponent,
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
  TinyMCEComponent,
  LogoComponent,
  UploadAvatarDialogComponent,
  ProgressComponent,
  ParentCreateComponent,
  AddParentComponent,
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
  UploadAvatarDialogComponent,
  ProgressComponent,
  ParentCreateComponent,
  AddParentComponent,
];

const MODULES = [
  RouterModule ,
  FormsModule,
  ReactiveFormsModule,
  FileUploadModule,
  LayoutModule,
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
  NbProgressBarModule,
  NbSpinnerModule,
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
    dialogClass: 'global-dialog-container',
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
