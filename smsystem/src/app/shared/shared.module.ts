import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import {MatTableModule} from '@angular/material/table';
import {MatSelectModule} from '@angular/material/select';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import { NbMomentDateModule } from '@nebular/moment';
import { FileUploadModule } from 'ng2-file-upload';
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
import { ThemeModule } from 'app/@theme/theme.module';

import {
  UploadsComponent,
  UserTableComponent,
  UserDetailsComponent,
  AddressComponent,
  DeleteModalComponent,
  UserDeleteComponent,
} from 'app/shared';



const COMPONENTS = [
  AddressComponent,
  DeleteModalComponent,
  UserDeleteComponent,
  UserTableComponent,
  UserDetailsComponent,
  UploadsComponent,
];

const ENTRY_COMPONENTS = [
  UserDeleteComponent,
  DeleteModalComponent,
  UploadsComponent,
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
export class SharedModule { }
