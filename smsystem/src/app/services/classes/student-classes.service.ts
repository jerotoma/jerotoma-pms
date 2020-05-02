import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { END_POINTS, QueryParam } from 'app/utils';

import { ResponseWrapper, StudentClassAdmission, StudentClass } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class StudentClassService {
  constructor(private http: HttpClient) { }

  getStudentClass(classId: number): Observable<StudentClass> {
    return this.http.get(`${END_POINTS.studentClasses}/${classId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }
  getStudentClasses(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get<any>(
        `${END_POINTS.studentClasses}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }
  createStudentClass(data?: any): Observable<StudentClassAdmission> {
    return this.http.post(`${END_POINTS.studentClasses}`, data).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteStudentClass(classId: number): Observable<boolean> {
    return this.http.delete<any>(`${END_POINTS.studentClasses}/${classId}`).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateStudentClass(data?: any): Observable<StudentClassAdmission> {
    return this.http.put<any>(`${END_POINTS.studentClasses}`, data) .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }
}
