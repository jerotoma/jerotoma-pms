
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

import { ScoreStandingsRoutingModule } from './score-standings-routing.module';
import { ScoreStandingsComponent } from './score-standings.component';
import { ScoreStandingsViewComponent } from './score-standing-view/score-standings-view.component';
import { ScoreStandingCreateComponent } from './score-standing-create/score-standing-create.component';
import { ScoreStandingDeleteComponent } from './score-standing-delete/score-standing-delete.component';


const COMPONENTS = [
  ScoreStandingsComponent,
  ScoreStandingsViewComponent,
  ScoreStandingCreateComponent,
  ScoreStandingDeleteComponent,
];

const ENTRY_COMPONENTS = [
  ScoreStandingCreateComponent,
  ScoreStandingDeleteComponent,
];

const MODULES = [
  SharedModule,
  ScoreStandingsRoutingModule,
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
export class ScoreStandingsModule { }
