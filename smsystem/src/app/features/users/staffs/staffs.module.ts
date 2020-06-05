import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import {MatTableModule} from '@angular/material/table';
import {MatSelectModule} from '@angular/material/select';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';

import { NbMomentDateModule } from '@nebular/moment';
import {
  NbAccordionModule,
  NbButtonModule,
  NbSpinnerModule,
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

import { LayoutModule } from 'app/layouts/layout.module';
import { StudentsRoutingModule } from './staffs-routing.module';

import { StaffsComponent } from './staffs.component';
import { StaffCreateComponent } from './create/staff-create.component';
import { StaffsViewComponent } from './views/staffs-view.component';
import { StaffShowComponent } from './show/staff-show.component';

const COMPONENTS = [
    StaffsComponent,
    StaffsViewComponent,
    StaffCreateComponent,
    StaffShowComponent,
];

const ENTRY_COMPONENTS = [
  StaffCreateComponent,
];

const MODULES = [
    SharedModule,
    LayoutModule,
    NbMomentDateModule,
    NbSpinnerModule,
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
    StudentsRoutingModule,
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
export class StaffsModule { }
