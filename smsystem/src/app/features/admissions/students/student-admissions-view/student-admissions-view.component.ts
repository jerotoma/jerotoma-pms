import { Component } from '@angular/core';

import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { PageEvent } from '@angular/material';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

@Component({
  selector: 'app-student-admissions-view',
  styleUrls: ['./student-admissions-view.component.scss'],
  templateUrl: './student-admissions-view.component.html',
})
export class StudentAdmissionsViewComponent {
  starRate = 2;
  heartRate = 4;
  radioGroupValue = 'This is value 2';
}
