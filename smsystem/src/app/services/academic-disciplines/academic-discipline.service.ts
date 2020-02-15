import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, map } from 'rxjs/operators';
import { END_POINTS, QueryParam } from 'app/utils';

import { AcademicDiscipline, ResponseWrapper } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class AcademicDisciplineService {

  constructor(private http: HttpClient) { }

  getAcademicDiscipline(academicDisciplineId: number): Observable<AcademicDiscipline> {
    return this.http
      .get(`${END_POINTS.academicDisciplines}/${academicDisciplineId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data ));
  }

  loadAcademicDisciplineList(): Observable<AcademicDiscipline[]> {
    return this.http.get(`${END_POINTS.academicDisciplines}/list`)
      .pipe(map((resp: ResponseWrapper) => resp.data ));
  }

  getAcademicDisciplines(param: QueryParam): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.get<any>(
        `${END_POINTS.academicDisciplines}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`,
        {observe: 'response'})
      .pipe(retry(3), catchError(this.errorHandler));
  }
  createAcademicDiscipline(data?: any): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.post<any>(`${END_POINTS.academicDisciplines}`, data, {observe: 'response'});
  }

  deleteAcademicDiscipline(academicDisciplineId: number): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.delete<any>(`${END_POINTS.academicDisciplines}/${academicDisciplineId}`, {observe: 'response'});
  }

  updateAcademicDiscipline(data?: any): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.put<any>(`${END_POINTS.academicDisciplines}`, data, {observe: 'response'});
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }
}
