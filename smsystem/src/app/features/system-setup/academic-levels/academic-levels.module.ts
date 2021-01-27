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
  NbSelectModule,
  NbCheckboxModule,
  NbListModule,
} from '@nebular/theme';

import { AcademicLevelsRoutingModule } from './academic-levels-routing.module';
import { AcademicLevelsComponent } from './academic-levels.component';
import { AcademicLevelListComponent } from './academic-level-list/academic-level-list.component';
import { AcademicLevelCreateComponent } from './academic-level-create/academic-level-create.component';
import {AcademicLevelShowComponent } from './academic-level-show/academic-level-show.component';

const COMPONENTS = [
  AcademicLevelsComponent,
  AcademicLevelCreateComponent,
  AcademicLevelListComponent,
  AcademicLevelShowComponent,
];

const ENTRY_COMPONENTS = [
  AcademicLevelCreateComponent,
];

const MODULES = [
  SharedModule,
  AcademicLevelsRoutingModule,
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
export class AcademicLevelsModule { }
