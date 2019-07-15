import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {  delay, map, catchError, retry } from 'rxjs/operators';
import { Data } from '@angular/router';

import { User } from '../../models/users';
import { END_POINTS, QueryParam } from '../../utils';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
  }),
};

@Injectable({
  providedIn: 'root',
})
export class UserService {

  constructor(private http: HttpClient) { }

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

  addUser(data: Data): Observable<any>{
    return this.http.post<any>(`${END_POINTS.users}`, data, httpOptions)
                    .pipe(catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse){
    return throwError(error.message || 'Server error');
  }
}
