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

import { RoomsRoutingModule } from './rooms-routing.module';
import { RoomsComponent } from './rooms.component';
import { RoomsViewComponent } from './rooms-view/rooms-view.component';
import { RoomCreateComponent } from './room-create/room-create.component';
import { RoomDeleteComponent } from './room-delete/room-delete.component';


const COMPONENTS = [
  RoomsComponent,
  RoomsViewComponent,
  RoomCreateComponent,
  RoomDeleteComponent,
];

const ENTRY_COMPONENTS = [
  RoomCreateComponent,
  RoomDeleteComponent,
];

const MODULES = [
  SharedModule,
  RoomsRoutingModule,
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
export class RoomsModule { }
