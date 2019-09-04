import { Component } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { PageEvent } from '@angular/material';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { Course } from 'app/models';
import { CourseService } from 'app/services';
import { QueryParam } from 'app/utils';
import { from } from 'rxjs';


@Component({
  selector: 'app-classes',
  template: `
    <router-outlet></router-outlet>
  `,
})
export class ClassesComponent {

}
