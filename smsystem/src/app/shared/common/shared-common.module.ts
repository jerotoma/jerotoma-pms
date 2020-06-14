import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatMenuModule} from '@angular/material/menu';
import { MatSnackBarModule, MAT_SNACK_BAR_DEFAULT_OPTIONS } from '@angular/material/snack-bar';

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
  MatTableModule,
  MatPaginatorModule,
  MatIconModule,
  MatMenuModule,
  MatSelectModule,
  MatSnackBarModule,
  MatButtonModule,
];

const EXPORT_MODULES = [

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
