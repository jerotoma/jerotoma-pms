import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {  delay, map, catchError, retry } from 'rxjs/operators';
import { Data } from '@angular/router';

import { User } from '../../models/users';
import { END_POINTS, QueryParam } from '../../utils';

@Injectable({
  providedIn: 'root',
})
export class UserService {

  constructor(private http: HttpClient) { }

  getCurrentUser(): Observable<any> {
    return this.http
      .get<any>(`${END_POINTS.users}/currentUser`)
      .pipe(retry(3), catchError(this.errorHandler));
  }

  getUser(userId: number): Observable<User> {
    return this.http
      .get<User>(`${END_POINTS.users}/${userId}`)
      .pipe(retry(3), catchError(this.errorHandler));
  }

  load(param: QueryParam): Observable<User[]> {
    return this.http
      .get<User[]>(`${END_POINTS.users}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
      .pipe(retry(3), catchError(this.errorHandler));
  }
  loadTeachers(param: QueryParam): Observable<any> {
    return this.http.get<any>(
      `${END_POINTS.users}?page=${param.page}&pageSize=${param.pageSize}
        &orderby=${param.orderby}&userType=${param.userType}`)
      .pipe(retry(3), catchError(this.errorHandler));
  }
  addUser(data: any): Observable<HttpResponse<any> | HttpErrorResponse >  {
    return this.http.post<any>(`${END_POINTS.users}`, data, {observe: 'response'});
  }
  search(param: QueryParam): Observable<any> {
    return this.http
    .get<User[]>(`${END_POINTS.users}/search?searchTerm=${param.search}
            &page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
            .pipe(retry(3), catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }
}
