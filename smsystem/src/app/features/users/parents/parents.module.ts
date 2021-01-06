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
import { StudentsRoutingModule } from './parents-routing.module';

import { ParentsComponent } from './parents.component';
import { ParentShowComponent } from './show/parent-show.component';
import { ParentsViewComponent } from './views/parents-view.component';

const COMPONENTS = [
    ParentsComponent,
    ParentsViewComponent,
    ParentShowComponent,
];

const ENTRY_COMPONENTS = [

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
      dialogClass: 'global-dialog-container',
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
export class ParentsModule { }
