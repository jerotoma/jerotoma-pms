import { NgModule } from '@angular/core';
import {DragDropModule} from '@angular/cdk/drag-drop';
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
  NbSelectModule,
  NbCheckboxModule,
  NbListModule,
} from '@nebular/theme';

import { ProgramsRoutingModule } from './programs-routing.module';
import { ProgramsComponent } from './programs.component';
import { ProgramListComponent } from './program-list/program-list.component';
import { ProgramCreateComponent } from './program-create/program-create.component';
import { ProgramEditComponent } from './program-edit/program-edit.component';
import { ProgramShowComponent } from './program-show/program-show.component';
import { CreateAcademicLevelComponent } from './program-show/create-academic-levels/create-academic-level.component';

const COMPONENTS = [
  ProgramsComponent,
  ProgramCreateComponent,
  ProgramListComponent,
  ProgramEditComponent,
  ProgramShowComponent,
  CreateAcademicLevelComponent
];

const ENTRY_COMPONENTS = [
  ProgramCreateComponent,
  ProgramEditComponent,
  ProgramShowComponent,
  CreateAcademicLevelComponent
];

const MODULES = [
  SharedModule,
  ProgramsRoutingModule,
  NbButtonModule,
  NbSpinnerModule,
  NbWindowModule,
  NbCardModule,
  NbInputModule,
  NbSelectModule,
  NbCheckboxModule,
  NbListModule,
  NbIconModule,
  NbAlertModule,
  MatTableModule,
  MatPaginatorModule,
  MatIconModule,
  DragDropModule,
  MatMenuModule,
  NbDialogModule.forRoot({
    closeOnBackdropClick: false,
    hasScroll: false,
    dialogClass: 'global-dialog-container',
  }),
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
export class ProgramsModule { }
