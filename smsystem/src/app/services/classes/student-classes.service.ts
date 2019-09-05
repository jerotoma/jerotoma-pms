import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { END_POINTS, QueryParam } from 'app/utils';

@Injectable({
  providedIn: 'root',
})
export class StudentClassService {
  constructor(private http: HttpClient) { }

  getClass(classId: number): Observable<any> {
    return this.http
      .get<any>(`${END_POINTS.studentClasses}/${classId}`)
      .pipe(retry(3), catchError(this.errorHandler));
  }

  loadStudentClassList(param: QueryParam): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.get<any>(
        `${END_POINTS.studentClasses}/list?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`,
        {observe: 'response'})
      .pipe(retry(3), catchError(this.errorHandler));
  }

  getStudentClasses(param: QueryParam): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.get<any>(
        `${END_POINTS.studentClasses}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`,
        {observe: 'response'})
      .pipe(retry(3), catchError(this.errorHandler));
  }
  createStudentClass(data?: any): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.post<any>(`${END_POINTS.studentClasses}`, data, {observe: 'response'});
  }

  deleteStudentClass(classId: number): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.delete<any>(`${END_POINTS.studentClasses}/${classId}`, {observe: 'response'});
  }

  updateStudentClass(data?: any): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.put<any>(`${END_POINTS.studentClasses}`, data, {observe: 'response'});
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }
}
