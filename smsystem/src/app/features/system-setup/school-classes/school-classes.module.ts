import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';

import {
  NbButtonModule,
  NbDialogModule,
  NbWindowModule,
  NbCardModule,
  NbInputModule,
  NbIconModule,
  NbAlertModule,
} from '@nebular/theme';

import { SchoolClassesRoutingModule } from './school-classes-routing.module';
import { SchoolClassesComponent } from './school-classes.component';
import { SchoolClassesViewComponent } from './school-classes-view/school-classes-view.component';
import {  SchoolClassCreateComponent } from './school-class-create/school-class-create.component';
import {  SchoolClassDeleteComponent } from './school-class-delete/school-class-delete.component';


const COMPONENTS = [
  SchoolClassesComponent,
  SchoolClassesViewComponent,
  SchoolClassCreateComponent,
  SchoolClassDeleteComponent,
];

const ENTRY_COMPONENTS = [
   SchoolClassCreateComponent,
   SchoolClassDeleteComponent,
];

const MODULES = [
  FormsModule,
  ReactiveFormsModule,
  SchoolClassesRoutingModule,
  NbButtonModule,
  NbWindowModule,
  NbCardModule,
  NbInputModule,
  NbIconModule,
  NbAlertModule,
  MatTableModule,
  MatPaginatorModule,
  MatIconModule,
  MatMenuModule,
  NbDialogModule.forRoot(),
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
export class SchoolClassesModule { }
