import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { API_END_POINTS, QueryParam } from 'app/utils';

import { ResponseWrapper, StudentClassAdmission, StudentClass } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class StudentClassService {
  constructor(private http: HttpClient) { }

  getStudentClass(classId: number): Observable<StudentClass> {
    return this.http.get(`${API_END_POINTS.studentClasses}/${classId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }
  getStudentClasses(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get<any>(
        `${API_END_POINTS.studentClasses}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }

  getStudentClassesByStudentId(studentId: number): Observable<StudentClass[]> {
    return this.http.get(`${API_END_POINTS.studentClasses}/students/${studentId}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  createStudentClass(data?: any): Observable<StudentClassAdmission> {
    return this.http.post(`${API_END_POINTS.studentClasses}`, data).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteStudentClass(classId: number): Observable<boolean> {
    return this.http.delete<any>(`${API_END_POINTS.studentClasses}/${classId}`).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateStudentClass(data?: any): Observable<StudentClassAdmission> {
    return this.http.put<any>(`${API_END_POINTS.studentClasses}`, data) .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }
}
