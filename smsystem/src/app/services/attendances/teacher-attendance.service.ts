import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, map } from 'rxjs/operators';
import { END_POINTS, QueryParam } from 'app/utils';

import { AcademicYear, ResponseWrapper } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class TeacherAttendanceService {

}
