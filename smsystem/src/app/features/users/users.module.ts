import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

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
import { UserRoutingModule } from './users-routing.module';

import { TeacherCreateComponent } from './teachers/create/teacher-create.component';
import { UserDeleteComponent } from './user-delete/user-delete.component';
import { UserTableComponent } from 'app/shared';
import { UsersComponent } from './users.component';
import { Tab1Component, Tab2Component, ParentsComponent } from './parents/parents.component';
import { TeachersComponent } from './teachers/teachers.component';
import { StudentsComponent } from './students/students.component';
import { StudentCreateComponent } from './students/create/student-create.component';
import { InfiniteListComponent } from './infinite-list/infinite-list.component';
import { NewsPostComponent } from './infinite-list/news-post/news-post.component';
import { NewsPostPlaceholderComponent } from './infinite-list/news-post-placeholder/news-post-placeholder.component';
import { OtherStaffsComponent } from './other-staffs/other-staffs.component';
import { NewsService } from './news.service';


const COMPONENTS = [
    UserTableComponent,
    UsersComponent,
    TeacherCreateComponent,
    StudentCreateComponent,
    UserDeleteComponent,
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
  StudentCreateComponent,
  UserDeleteComponent,
];

const MODULES = [
    FormsModule,
    ReactiveFormsModule,
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
    UserRoutingModule,
    NbIconModule,
    MatTableModule,
    MatPaginatorModule,
    MatIconModule,
    MatMenuModule,
    MatSelectModule,
    NbDialogModule.forRoot({
      closeOnBackdropClick: false,
      hasScroll: true,

    }),
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
