import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
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
} from '@nebular/theme';

import { TeacherCreateComponent } from './teachers/create/teacher-create.component';
import { UserTableComponent } from '../../shared';
import { ThemeModule } from '../../@theme/theme.module';
import { LayoutRoutingModule } from './users-routing.module';
import { UsersComponent } from './users.component';
import { Tab1Component, Tab2Component, ParentsComponent } from './parents/parents.component';
import { TeachersComponent } from './teachers/teachers.component';
import { StudentsComponent } from './students/students.component';
import { InfiniteListComponent } from './infinite-list/infinite-list.component';
import { NewsPostComponent } from './infinite-list/news-post/news-post.component';
import { NewsPostPlaceholderComponent } from './infinite-list/news-post-placeholder/news-post-placeholder.component';
import { OtherStaffsComponent } from './other-staffs/other-staffs.component';
import { NewsService } from './news.service';


const COMPONENTS = [
    UserTableComponent,
    UsersComponent,
    TeacherCreateComponent,
    ParentsComponent,
    Tab1Component,
    Tab2Component,
    TeachersComponent,
    StudentsComponent,
    NewsPostPlaceholderComponent,
    InfiniteListComponent,
    NewsPostComponent,
    OtherStaffsComponent,
];

const ENTRY_COMPONENTS = [
  TeacherCreateComponent,
];

const MODULES = [
    FormsModule,
    ReactiveFormsModule,
    ThemeModule,
    NbMomentDateModule,
    NbTabsetModule,
    NbRadioModule,
    NbDatepickerModule,
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
    LayoutRoutingModule,
    NbIconModule,
    NbDialogModule.forRoot(),
    NbWindowModule.forRoot(),
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
