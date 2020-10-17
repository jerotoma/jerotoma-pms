import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, map } from 'rxjs/operators';
import { API_END_POINTS, QueryParam } from 'app/utils';

import { AcademicYear, ResponseWrapper } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class AcademicYearService {
   constructor(private http: HttpClient) { }

  getAcademicYear(academicYearId: number): Observable<any> {
    return this.http
      .get<any>(`${API_END_POINTS.academicYears}/${academicYearId}`)
      .pipe(retry(3), catchError(this.errorHandler));
  }

  loadAcademicYearPaginated(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get(
        `${API_END_POINTS.academicYears}/paginated?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }

  getAcademicYears(): Observable<AcademicYear[]> {
    return this.http.get(
        `${API_END_POINTS.academicYears}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  findAcademicYearsByStudentLevel(studentId: number, academicLevelId: number): Observable<AcademicYear[]> {
    return this.http
      .get<any>(`${API_END_POINTS.academicYears}/students/${studentId}/academic-levels/${academicLevelId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }
  createAcademicYear(data?: any): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.post<any>(`${API_END_POINTS.academicYears}`, data, {observe: 'response'});
  }

  deleteAcademicYear(academicYearId: number): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.delete<any>(`${API_END_POINTS.academicYears}/${academicYearId}`, {observe: 'response'});
  }

  updateAcademicYear(data?: any): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.put<any>(`${API_END_POINTS.academicYears}`, data, {observe: 'response'});
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }

  getCurrentAcademicYear(): Observable<AcademicYear> {
    return this.http
      .get(`${API_END_POINTS.academicYears}/currentAcademicYear`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }
}
