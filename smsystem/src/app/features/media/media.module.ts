import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatTableModule } from '@angular/material/table';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { NbMomentDateModule } from '@nebular/moment';

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
  NbDatepickerModule,
  NbTabsetModule,
  NbListModule,
  NbRadioModule,
} from '@nebular/theme';

import { MediaRoutingModule } from './media-routing.module';
import { MediaComponent } from './media.component';
import { MediaListComponent } from './media-list/media-list.component';
import { MediaStudentsComponent } from './media-students/media-students.component';
import { MediaTeachersComponent } from './media-teachers/media-teachers.component';
import { MediaStaffsComponent } from './media-staffs/media-staffs.component';
import { MediaCreateComponent } from './media-create/media-create.component';
import { MediaShowComponent } from './media-show/media-show.component';

const COMPONENTS = [
  MediaComponent,
  MediaListComponent,
  MediaCreateComponent,
  MediaStudentsComponent,
  MediaTeachersComponent,
  MediaStaffsComponent,
];

const ENTRY_COMPONENTS = [

];

const MODULES = [
  SharedModule,
  MediaRoutingModule,
  NgbModule,
  NbMomentDateModule,
  NbDatepickerModule,
  NbButtonModule,
  NbSpinnerModule,
  NbWindowModule,
  NbCardModule,
  NbInputModule,
  NbSelectModule,
  NbCheckboxModule,
  NbTabsetModule,
  NbListModule,
  NbIconModule,
  NbAlertModule,
  MatTableModule,
  MatPaginatorModule,
  NbRadioModule,
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
export class MediaModule { }
