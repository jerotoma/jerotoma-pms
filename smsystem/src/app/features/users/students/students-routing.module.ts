// Modules
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Service
import { AuthGuard } from 'app/services/guards/auth-guard.service';

// Components
import { StudentsComponent } from './students.component';
import { StudentCreateComponent } from './create/student-create.component';
import { StudentsViewComponent } from './views/students-view.component';

// Utilities

const routes: Routes = [
  {
    path: '',
    canActivateChild: [AuthGuard],
    component: StudentsComponent,
    children: [
        {
          path: '',
          component: StudentsViewComponent,
        },
        {
          path: 'create',
          component: StudentCreateComponent,
        },
        {
          path: 'views',
          component: StudentsViewComponent,
        },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class StudentsRoutingModule {
}
