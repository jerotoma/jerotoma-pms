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

import { AcademicYearsRoutingModule } from './academic-years-routing.module';
import { AcademicYearsComponent } from './academic-years.component';
import { AcademicYearsViewComponent } from './academic-years-view/academic-years-view.component';
import { AcademicYearCreateComponent } from './academic-year-create/academic-year-create.component';
import { AcademicYearDeleteComponent } from './academic-year-delete/academic-year-delete.component';


const COMPONENTS = [
  AcademicYearsComponent,
  AcademicYearsViewComponent,
  AcademicYearCreateComponent,
  AcademicYearDeleteComponent,
];

const ENTRY_COMPONENTS = [
  AcademicYearCreateComponent,
  AcademicYearDeleteComponent,
];

const MODULES = [
  FormsModule,
  ReactiveFormsModule,
  AcademicYearsRoutingModule,
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
export class AcademicYearsModule { }
