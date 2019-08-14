import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse, HttpEvent } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {  delay, map, catchError, retry } from 'rxjs/operators';
import { Data } from '@angular/router';

import { User } from '../../models/users';
import { END_POINTS, QueryParam } from '../../utils';

@Injectable({
  providedIn: 'root',
})
export class PositionService {

  constructor(private http: HttpClient) { }

  getPosition(positionId: number): Observable<any> {
    return this.http
      .get<any>(`${END_POINTS.positions}/${positionId}`)
      .pipe(retry(3), catchError(this.errorHandler));
  }

  loadPositionList(param: QueryParam): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.get<any>(
        `${END_POINTS.positions}/list?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`,
        {observe: 'response'})
      .pipe(retry(3), catchError(this.errorHandler));
  }

  getPositions(param: QueryParam): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.get<any>(
        `${END_POINTS.positions}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`,
        {observe: 'response'})
      .pipe(retry(3), catchError(this.errorHandler));
  }
  createPosition(data?: any): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.post<any>(`${END_POINTS.positions}`, data, {observe: 'response'});
  }

  deletePosition(positionId: number): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.delete<any>(`${END_POINTS.positions}/${positionId}`, {observe: 'response'});
  }

  updatePosition(data?: any): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.put<any>(`${END_POINTS.positions}`, data, {observe: 'response'});
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }
}
