import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharedModule } from 'app/shared/shared.module';
import { MatTableModule } from '@angular/material/table';
import { MatSelectModule } from '@angular/material/select';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';

import { NbMomentDateModule } from '@nebular/moment';
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
  NbContextMenuModule,
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

import { HeaderRoutingModule } from './header-routing.module';
import { ProfileComponent, EditUserComponent } from './profile';
import { PreferencesComponent } from './preferences';

@NgModule({
  declarations: [
    ProfileComponent,
    PreferencesComponent,
    EditUserComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    NbDialogModule,
    NbWindowModule,
    NbMomentDateModule,
    NbSpinnerModule,
    NbTabsetModule,
    NbContextMenuModule,
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
    HeaderRoutingModule,
  ],
})
export class HeaderModule { }
