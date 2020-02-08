import { NgModule } from '@angular/core';

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
import { NbMomentDateModule } from '@nebular/moment';

export const MODULES = [
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
  NbDialogModule,
  NbWindowModule,
  NbDialogModule.forRoot({
    closeOnBackdropClick: false,
    hasScroll: false,

  }),
  NbWindowModule.forRoot(),
];

export const EXPORT_MODULES = [
];

export const SERVICES = [

];

