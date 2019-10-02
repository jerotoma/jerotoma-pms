
import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';

import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';

import {
  NbButtonModule,
  NbSpinnerModule,
  NbDialogModule,
  NbWindowModule,
  NbCardModule,
  NbInputModule,
  NbIconModule,
  NbAlertModule,
} from '@nebular/theme';

import { CoursesRoutingModule } from './courses-routing.module';
import { CoursesComponent } from './courses.component';
import { CoursesViewComponent } from './course-view/courses-view.component';
import { CourseCreateComponent } from './course-create/course-create.component';
import { CourseDeleteComponent } from './course-delete/course-delete.component';


const COMPONENTS = [
  CoursesComponent,
  CoursesViewComponent,
  CourseCreateComponent,
  CourseDeleteComponent,
];

const ENTRY_COMPONENTS = [
  CourseCreateComponent,
  CourseDeleteComponent,
];

const MODULES = [
  SharedModule,
  CoursesRoutingModule,
  NbButtonModule,
  NbSpinnerModule,
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
export class CoursesModule { }
