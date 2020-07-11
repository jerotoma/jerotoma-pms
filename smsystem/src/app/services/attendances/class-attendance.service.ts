import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, map } from 'rxjs/operators';
import { API_END_POINTS, QueryParam } from 'app/utils';

import { AcademicYear, ResponseWrapper, ClassAttendanceParam, ClassAttendance } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class ClassAttendanceService {

  constructor(private http: HttpClient) { }

  getClassAttendance(classAttendanceId: number): Observable<ClassAttendance> {
    return this.http.get(`${API_END_POINTS.attendances}/classes/${classAttendanceId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadClassAttendancesPaginated(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get(`${API_END_POINTS.attendances}/classes/paginated?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
      .pipe(map((resp: ResponseWrapper) => resp));
  }

  createClassAttendance(classAttendanceParam: ClassAttendanceParam): Observable<ClassAttendance> {
    return this.http.post(`${API_END_POINTS.attendances}/classes`, classAttendanceParam)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadClassAttendances(): Observable<ClassAttendance[]> {
    return this.http.get(`${API_END_POINTS.attendances}/classes`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

}
