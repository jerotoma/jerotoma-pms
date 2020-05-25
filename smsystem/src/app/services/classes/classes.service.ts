import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { END_POINTS, QueryParam } from 'app/utils';

import { ResponseWrapper, ClassView } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class ClassService {
  constructor(private http: HttpClient) { }

  getClass(classId: number): Observable<ClassView>  {
    return this.http.get(`${END_POINTS.classes}/${classId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadClassList(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get(`${END_POINTS.classes}/list?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }
  loadJClassesByAcademicYear(academicYearId: number): Observable<ClassView[]> {
    return this.http.get(`${END_POINTS.classes}/academic-years/${academicYearId}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getClasses(param: QueryParam): Observable<ResponseWrapper>  {
    return this.http.get(`${END_POINTS.classes}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }
  createClass(data?: any): Observable<ClassView> {
    return this.http.post<any>(`${END_POINTS.classes}`, data)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  postAutoGenerateClasses(data: any) {
    return this.http.post(`${END_POINTS.classes}/auto-generate`, data)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }
  deleteClass(classId: number): Observable<boolean> {
    return this.http.delete<any>(`${END_POINTS.classes}/${classId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateClass(data?: any): Observable<ClassView> {
    return this.http.put(`${END_POINTS.classes}`, data).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadUnregisteredJClassesByStudent(academicYearId: number, studentId: number) {
    return this.http.get(`${END_POINTS.classes}/academic-years/${academicYearId}/students/${studentId}/unregistered`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadStudentJClassesByAcademicYear(academicYearId: number, studentId: number) {
    return this.http.get(`${END_POINTS.classes}/academic-years/${academicYearId}/students/${studentId}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }
}
