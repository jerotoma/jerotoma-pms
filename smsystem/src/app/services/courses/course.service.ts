import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, map } from 'rxjs/operators';
import { END_POINTS, QueryParam } from 'app/utils';

import { ResponseWrapper, Course } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  constructor(private http: HttpClient) { }

  getCourse(courseId: number): Observable<any> {
    return this.http
      .get<any>(`${END_POINTS.courses}/${courseId}`)
      .pipe(retry(3), catchError(this.errorHandler));
  }

  getCoursesByAcademicYearId(academicYearId: number): Observable<Course[]> {
    return this.http.get(`${END_POINTS.courses}/academicYears/${academicYearId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadCourseList(param: QueryParam): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.get<any>(
        `${END_POINTS.courses}/list?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`,
        {observe: 'response'})
      .pipe(retry(3), catchError(this.errorHandler));
  }

  getCourses(param: QueryParam): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.get<any>(
        `${END_POINTS.courses}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`,
        {observe: 'response'})
      .pipe(retry(3), catchError(this.errorHandler));
  }
  createCourse(data?: any): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.post<any>(`${END_POINTS.courses}`, data, {observe: 'response'});
  }

  deleteCourse(courseId: number): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.delete<any>(`${END_POINTS.courses}/${courseId}`, {observe: 'response'});
  }

  updateCourse(data?: any): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.put<any>(`${END_POINTS.courses}`, data, {observe: 'response'});
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }
}
