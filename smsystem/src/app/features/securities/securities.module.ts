import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';

import {MatTableModule} from '@angular/material/table';
import {MatSelectModule} from '@angular/material/select';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';

import { NbMomentDateModule } from '@nebular/moment';
import { ThemeModule } from 'app/@theme/theme.module';
import {
  NbAccordionModule,
  NbSpinnerModule,
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

import { SecuritiesComponent } from './securities.component';
import { RolesComponent } from './roles/roles.component';
import { PermissionsComponent } from './permissions/permissions.component';
import { SecuritiesRoutingModule } from './securities-routing.module';

const COMPONENTS = [
    SecuritiesComponent,
    RolesComponent,
    PermissionsComponent,
];

const ENTRY_COMPONENTS = [

];

const MODULES = [
    SharedModule,
    NbSpinnerModule,
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
    SecuritiesRoutingModule,
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
export class SecuritiesModule { }
