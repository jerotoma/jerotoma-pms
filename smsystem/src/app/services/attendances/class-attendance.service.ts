import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, map } from 'rxjs/operators';
import { END_POINTS, QueryParam } from 'app/utils';

import { AcademicYear, ResponseWrapper, ClassAttendanceParam, ClassAttendance } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class ClassAttendanceService {
  loadClassAttendancesPaginated(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get(`${END_POINTS.attendances}/classes?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
      .pipe(map((resp: ResponseWrapper) => resp));
  }

  constructor(private http: HttpClient) { }

  createClassAttendance(classAttendanceParam: ClassAttendanceParam): Observable<ClassAttendance> {
    return this.http.post(`${END_POINTS.attendances}/classes`, classAttendanceParam)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

}
