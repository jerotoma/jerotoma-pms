import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';

import {MatTableModule} from '@angular/material/table';
import {MatSelectModule} from '@angular/material/select';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';

import { NbMomentDateModule } from '@nebular/moment';
import { ThemeModule } from 'app/@theme/theme.module';
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

import { UserRoutingModule } from './users-routing.module';

import {
  Tab1Component,
  Tab2Component,
  ParentsComponent,
} from './parents/parents.component';

import { TeacherCreateComponent } from './teachers/create/teacher-create.component';
import { UsersComponent } from './users.component';

import { TeachersComponent } from './teachers/teachers.component';
import { InfiniteListComponent } from './infinite-list/infinite-list.component';
import { NewsPostComponent } from './infinite-list/news-post/news-post.component';
import { NewsPostPlaceholderComponent } from './infinite-list/news-post-placeholder/news-post-placeholder.component';
import { StaffsComponent } from './staffs/staffs.component';
import { NewsService } from './news.service';


const COMPONENTS = [
    UsersComponent,
    TeacherCreateComponent,
    ParentsComponent,
    Tab1Component,
    Tab2Component,
    TeachersComponent,
    NewsPostPlaceholderComponent,
    InfiniteListComponent,
    NewsPostComponent,
    StaffsComponent,
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
    UserRoutingModule,
];

const SERVICES = [
  NewsService,
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
export class UsersModule { }
