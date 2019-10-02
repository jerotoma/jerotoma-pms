import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';

import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';

import {
  NbActionsModule,
  NbButtonModule,
  NbDialogModule,
  NbWindowModule,
  NbSpinnerModule,
  NbToastrModule,
  NbCardModule,
  NbInputModule,
  NbIconModule,
  NbAccordionModule,
  NbListModule,
  NbSelectModule,
  NbRadioModule,
  NbDatepickerModule,
  NbRouteTabsetModule,
  NbStepperModule,
  NbTabsetModule,
  NbUserModule,
  NbCheckboxModule,
  NbAlertModule,
} from '@nebular/theme';

import { ClassesRoutingModule } from './classes-routing.module';
import { ClassesComponent } from './classes.component';
import { ClassCreateComponent } from './class-create/class-create.component';
import { ClassesViewComponent } from './classes-view/classes-view.component';


const COMPONENTS = [
  ClassesComponent,
  ClassesViewComponent,
  ClassCreateComponent,
];

const ENTRY_COMPONENTS = [
  ClassCreateComponent,
];

const MODULES = [
  SharedModule,
  MatTableModule,
  MatPaginatorModule,
  MatIconModule,
  NbActionsModule,
  NbAccordionModule,
  NbListModule,
  NbSelectModule,
  NbSpinnerModule,
  NbRadioModule,
  NbDatepickerModule,
  NbRouteTabsetModule,
  NbStepperModule,
  NbTabsetModule,
  NbUserModule,
  NbCheckboxModule,
  MatMenuModule,
  NbButtonModule,
  NbWindowModule,
  NbCardModule,
  NbInputModule,
  NbIconModule,
  NbAlertModule,
  NbDialogModule.forRoot(),
  NbToastrModule.forRoot(),
  ClassesRoutingModule,
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
export class ClassesModule { }
