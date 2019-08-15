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

import { AcademicDisciplinesRoutingModule } from './academic-disciplines-routing.module';
import { AcademicDisciplinesComponent } from './academic-disciplines.component';
import { AcademicDisciplinesViewComponent } from './discipline-view/academic-disciplines-view.component';
import { AcademicDisciplineCreateComponent } from './discipline-create/academic-discipline-create.component';
import { AcademicDisciplineDeleteComponent } from './discipline-delete/academic-discipline-delete.component';


const COMPONENTS = [
  AcademicDisciplinesComponent,
  AcademicDisciplinesViewComponent,
  AcademicDisciplineCreateComponent,
  AcademicDisciplineDeleteComponent,
];

const ENTRY_COMPONENTS = [
  AcademicDisciplineCreateComponent,
  AcademicDisciplineDeleteComponent,
];

const MODULES = [
  FormsModule,
  ReactiveFormsModule,
  AcademicDisciplinesRoutingModule,
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
export class AcademicDisciplinesModule { }
