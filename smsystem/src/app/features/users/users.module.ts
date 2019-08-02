
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  NbAccordionModule,
  NbButtonModule,
  NbCardModule,
  NbListModule,
  NbRouteTabsetModule,
  NbStepperModule,
  NbTabsetModule,
  NbActionsModule,
  NbIconModule,
  NbUserModule,
} from '@nebular/theme';

import { UserTableComponent } from './../../shared-components';
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

@NgModule({
  imports: [
    FormsModule,
    ReactiveFormsModule,
    ThemeModule,
    NbTabsetModule,
    NbRouteTabsetModule,
    NbStepperModule,
    NbCardModule,
    NbButtonModule,
    NbListModule,
    NbAccordionModule,
    NbUserModule,
    NbActionsModule,
    LayoutRoutingModule,
    NbIconModule,
  ],
  declarations: [
    UserTableComponent,
    UsersComponent,
    ParentsComponent,
    Tab1Component,
    Tab2Component,
    TeachersComponent,
    StudentsComponent,
    NewsPostPlaceholderComponent,
    InfiniteListComponent,
    NewsPostComponent,
    OtherStaffsComponent,
  ],
  providers: [
    NewsService,
  ],
})
export class UsersModule { }
