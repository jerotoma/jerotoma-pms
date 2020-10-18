
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

import { CompletionOrdersRoutingModule } from './completion-orders-routing.module';
import { CompletionOrdersComponent } from './completion-orders.component';
import { CompletionOrdersViewComponent } from './completion-order-view/completion-orders-view.component';
import { CompletionOrderCreateComponent } from './completion-order-create/completion-order-create.component';
import { CompletionOrderDeleteComponent } from './completion-order-delete/completion-order-delete.component';


const COMPONENTS = [
  CompletionOrdersComponent,
  CompletionOrdersViewComponent,
  CompletionOrderCreateComponent,
  CompletionOrderDeleteComponent,
];

const ENTRY_COMPONENTS = [
  CompletionOrderCreateComponent,
  CompletionOrderDeleteComponent,
];

const MODULES = [
  SharedModule,
  CompletionOrdersRoutingModule,
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
export class CompletionOrdersModule { }
