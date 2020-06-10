
import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
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

import { SchedulesRoutingModule } from './schedules-routing.module';
import { SchedulesComponent } from './schedules.component';
import { MeetingTimesViewComponent } from './meeting-times/meeting-times-view.component';
import { MeetingTimesCreateComponent } from './meeting-times/meeting-times-create/meeting-times-create.component';
import { MeetingTimesDeleteComponent } from './meeting-times/meeting-times-delete/meeting-times-delete.component';
import { WorkDaysComponent } from './work-days/work-days.component';
import { WorkDaysCreateComponent } from './work-days/work-days-create/work-days-create.component';
import { WorkDaysDeleteComponent } from './work-days/work-days-delete/work-days-delete.component';
import { TimetableComponent } from './timetable/timetable.component';


const COMPONENTS = [
  SchedulesComponent ,
  MeetingTimesViewComponent,
  MeetingTimesCreateComponent,
  MeetingTimesDeleteComponent,
  WorkDaysCreateComponent,
  WorkDaysDeleteComponent,
  WorkDaysComponent,
  TimetableComponent,
];

const ENTRY_COMPONENTS = [
  SchedulesComponent ,
  MeetingTimesViewComponent,
  MeetingTimesCreateComponent,
  MeetingTimesDeleteComponent,
  WorkDaysCreateComponent,
  WorkDaysDeleteComponent,
  WorkDaysComponent,
];

const MODULES = [
  SharedModule,
  SchedulesRoutingModule,
  NgbModule,
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
export class SchedulesModule { }
