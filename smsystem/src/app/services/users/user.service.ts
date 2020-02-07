import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError, retry } from 'rxjs/operators';

import { User, ResponseWrapper } from 'app/models';
import { END_POINTS, QueryParam } from 'app/utils';

@Injectable({
  providedIn: 'root',
})
export class UserService {

  constructor(private http: HttpClient) { }

  getCurrentUser(): Observable<User> {
    return this.http
      .get(`${END_POINTS.users}/currentUser`).
      pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getUser(userId: number): Observable<User> {
    return this.http
      .get(`${END_POINTS.users}/${userId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadUser(userId: number, userType: string): Observable<User> {
    return this.http
    .get(`${END_POINTS.users}/${userId}?userType=${userType}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadUserByUsername(username: string, userType: string): Observable<User> {
    return this.http
    .post(`${END_POINTS.users}/loggedIn`, {username: username, userType: userType})
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  load(param: QueryParam): Observable<User[]> {
    return this.http
      .get<User[]>(`${END_POINTS.users}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
      .pipe(retry(3), catchError(this.errorHandler));
  }

  loadUsers(param: QueryParam): Observable<any> {
    return this.http.get<any>(
      `${END_POINTS.users}?page=${param.page}&pageSize=${param.pageSize}
        &orderby=${param.orderby}&userType=${param.userType}`,  {observe: 'response'});
  }

  addUser(data: any): Observable<HttpResponse<any> | HttpErrorResponse >  {
    return this.http.post<any>(`${END_POINTS.users}`, data, {observe: 'response'});
  }

  updateUser(data: any): Observable<HttpResponse<any> | HttpErrorResponse >  {
    return this.http.put<any>(`${END_POINTS.users}`, data, {observe: 'response'});
  }

  deleteUser(userId: number, userType: string): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.delete<any>(`${END_POINTS.users}/${userId}?userType=${userType}`, {observe: 'response'});
  }

  search(param: QueryParam): Observable<any> {
    return this.http
    .get<any>(`${END_POINTS.users}/search?searchTerm=${param.search}
            &page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}&userType=${param.userType}`)
            .pipe(retry(3), catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }
}
