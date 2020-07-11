import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, map } from 'rxjs/operators';
import { API_END_POINTS, QueryParam } from 'app/utils';

import { AcademicYear, ResponseWrapper, StudentAttendanceParam, StudentAttendance } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class StudentAttendanceService {


  constructor(private http: HttpClient) { }

  createStudentAttendance(studentAttendanceParam: StudentAttendanceParam): Observable<StudentAttendance> {
    return this.http.post(`${API_END_POINTS.attendances}/students`, studentAttendanceParam)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadStudentAttendancesPaginated(param: QueryParam) : Observable<ResponseWrapper> {
    return this.http.get(`${API_END_POINTS.attendances}/students?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
    .pipe(map((resp: ResponseWrapper) => resp));
  }

}
