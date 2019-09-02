import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';

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

import { ClassRoomsRoutingModule } from './class-rooms-routing.module';
import { ClassRoomsComponent } from './class-rooms.component';
import { ClassRoomsViewComponent } from './class-rooms-view/class-rooms-view.component';
import { ClassRoomCreateComponent } from './class-room-create/class-room-create.component';
import { ClassRoomDeleteComponent } from './class-room-delete/class-room-delete.component';


const COMPONENTS = [
  ClassRoomsComponent,
  ClassRoomsViewComponent,
  ClassRoomCreateComponent,
  ClassRoomDeleteComponent,
];

const ENTRY_COMPONENTS = [
  ClassRoomCreateComponent,
  ClassRoomDeleteComponent,
];

const MODULES = [
  SharedModule,
  ClassRoomsRoutingModule,
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
export class ClassRoomsModule { }
