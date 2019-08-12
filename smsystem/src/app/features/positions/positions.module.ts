import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatIconModule} from '@angular/material/icon';
import { ThemeModule } from 'app/@theme/theme.module';
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


const COMPONENTS = [
  PositionsComponent,
  PositionsViewComponent,
  PositionCreateComponent,
];

const ENTRY_COMPONENTS = [
  PositionCreateComponent,
];

const MODULES = [
  FormsModule,
  ReactiveFormsModule,
  PositionsRoutingModule,
  ThemeModule,
  NbButtonModule,
  NbWindowModule,
  NbCardModule,
  NbInputModule,
  NbIconModule,
  NbAlertModule,
  MatTableModule,
  MatPaginatorModule,
  MatIconModule,
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
