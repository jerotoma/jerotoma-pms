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

import { PositionsRoutingModule } from './positions-routing.module';
import { PositionsComponent } from './positions.component';
import { PositionsViewComponent } from './positions-view/positions-view.component';
import { PositionCreateComponent } from './position-create/position-create.component';
import { PositionDeleteComponent } from './position-delete/position-delete.component';


const COMPONENTS = [
  PositionsComponent,
  PositionsViewComponent,
  PositionCreateComponent,
  PositionDeleteComponent,
];

const ENTRY_COMPONENTS = [
  PositionCreateComponent,
  PositionDeleteComponent,
];

const MODULES = [
  FormsModule,
  ReactiveFormsModule,
  PositionsRoutingModule,
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
export class PositionsModule { }
