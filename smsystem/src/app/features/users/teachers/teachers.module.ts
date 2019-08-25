import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import {MatTableModule} from '@angular/material/table';
import {MatSelectModule} from '@angular/material/select';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';

import { NbMomentDateModule } from '@nebular/moment';
import {
  NbAccordionModule,
  NbButtonModule,
  NbDialogModule,
  NbWindowModule,
  NbCardModule,
  NbListModule,
  NbSelectModule,
  NbRadioModule,
  NbDatepickerModule,
  NbRouteTabsetModule,
  NbStepperModule,
  NbTabsetModule,
  NbActionsModule,
  NbInputModule,
  NbIconModule,
  NbUserModule,
  NbCheckboxModule,
  NbAlertModule,
} from '@nebular/theme';

import { ThemeModule } from 'app/@theme/theme.module';
import { TeachersRoutingModule } from './teachers-routing.module';

import { TeachersComponent } from './teachers.component';
import { TeacherCreateComponent } from './create/teacher-create.component';
import { TeachersViewComponent } from './views/teachers-view.component';

const COMPONENTS = [
    TeachersComponent,
    TeachersViewComponent,
    TeacherCreateComponent,
];

const ENTRY_COMPONENTS = [
  TeacherCreateComponent,
];

const MODULES = [
    SharedModule,
    ThemeModule,
    NbMomentDateModule,
    NbTabsetModule,
    NbRadioModule,
    NbDatepickerModule,
    NbAlertModule,
    NbRouteTabsetModule,
    NbStepperModule,
    NbCardModule,
    NbCheckboxModule,
    NbSelectModule,
    NbInputModule,
    NbButtonModule,
    NbListModule,
    NbAccordionModule,
    NbUserModule,
    NbActionsModule,
    NbIconModule,
    MatTableModule,
    MatPaginatorModule,
    MatIconModule,
    MatMenuModule,
    MatSelectModule,
    NbDialogModule.forRoot({
      closeOnBackdropClick: false,
      hasScroll: false,

    }),
    NbWindowModule.forRoot(),
    TeachersRoutingModule,
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
export class TeachersModule { }
