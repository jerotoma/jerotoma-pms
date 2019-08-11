import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';

import { PositionsRoutingModule } from './positions-routing.module';
import { PositionsComponent } from './positions.component';

import { PositionsViewComponent } from './positions-view/positions-view.component';

const COMPONENTS = [
  PositionsComponent,
  PositionsViewComponent,
];

const ENTRY_COMPONENTS = [

];

const MODULES = [
  FormsModule,
  ReactiveFormsModule,
  PositionsRoutingModule,
  MatTableModule,
  MatPaginatorModule,
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
