import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UsersComponent } from './users.component';
import { Tab1Component, Tab2Component, ParentsComponent } from './parents/parents.component';
import { OtherStaffsComponent } from './other-staffs/other-staffs.component';
import { InfiniteListComponent } from './infinite-list/infinite-list.component';
import { StudentsComponent } from './students/students.component';
import { TeachersComponent } from './teachers/teachers.component';

const routes: Routes = [{
  path: '',
  component: UsersComponent,
  children: [
    {
      path: 'teachers',
      component: TeachersComponent,
    },
    {
      path: 'students',
      component: StudentsComponent,
    },
    {
      path: 'infinite-list',
      component: InfiniteListComponent,
    },
    {
      path: 'other-staffs',
      component: OtherStaffsComponent,
    },
    {
      path: 'parents',
      component: ParentsComponent,
      children: [
        {
          path: '',
          redirectTo: 'tab1',
          pathMatch: 'full',
        },
        {
          path: 'tab1',
          component: Tab1Component,
        },
        {
          path: 'tab2',
          component: Tab2Component,
        },
      ],
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class LayoutRoutingModule {
}
