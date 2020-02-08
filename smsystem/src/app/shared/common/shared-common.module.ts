import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
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

import {
  SnackbarComponent,
  DialogComponent,
} from 'app/shared';


const COMPONENTS = [
  SnackbarComponent,
  DialogComponent,
];

const ENTRY_COMPONENTS = [
  SnackbarComponent,
  DialogComponent,
];

const MODULES = [
  CommonModule,
  MatTableModule,
  MatPaginatorModule,
  MatIconModule,
  MatMenuModule,
  MatSelectModule,
  MatSnackBarModule,
  MatButtonModule,
  NbDialogModule,
  NbWindowModule,
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
];

const EXPORT_MODULES = [

];

const SERVICES = [
  MatDialog,
  {
    provide: MAT_SNACK_BAR_DEFAULT_OPTIONS,
    useValue: {
      duration: 6500,
     },
   },
];


@NgModule({
  imports: [
    ...MODULES,
  ],
  declarations: [
    ...COMPONENTS,
  ],
  exports: [
    ...EXPORT_MODULES,
    ...COMPONENTS,
  ],
  providers: [
   ...SERVICES,
  ],
  entryComponents: [
    ...ENTRY_COMPONENTS,
  ],
})
export class SharedCommonModule { }
