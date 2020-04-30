import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError, retry } from 'rxjs/operators';

import { User, ResponseWrapper } from 'app/models';
import { END_POINTS, QueryParam, USER_TYPE } from 'app/utils';

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
    switch (userType) {
        case USER_TYPE.parent:
          userType = 'parents';
        break;
        case USER_TYPE.staff:
          userType = 'staffs';
        break;
        case USER_TYPE.teacher:
          userType = 'teachers';
        break;
        case USER_TYPE.student:
          userType = 'students';
        break;
    }
    return this.http
    .get(`${END_POINTS.users}/${userType}/${userId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadUserDetails(username: string): Observable<User> {
    return this.http
    .post(`${END_POINTS.users}/profile`, {username: username})
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  load(param: QueryParam): Observable<any> {
    return this.http
      .get(`${END_POINTS.users}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadUsers(param: QueryParam): Observable<any> {
    return this.http
      .get(`${END_POINTS.users}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}&userType=${param.userType}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  addUser(data: any): Observable<ResponseWrapper>  {
    return this.http.post(`${END_POINTS.users}`, data)
    .pipe(map((resp: ResponseWrapper) => resp));
  }

  updateUser(data: any): Observable<ResponseWrapper>  {
    return this.http.put(`${END_POINTS.users}`, data)
    .pipe(map((resp: ResponseWrapper) => resp));
  }

  deleteUser(userId: number, userType: string): Observable<User> {
    return this.http.delete(`${END_POINTS.users}/${userId}?userType=${userType}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  search(param: QueryParam): Observable<User[]> {
    return this.http
    .get(`${END_POINTS.users}/search?searchTerm=${param.search}&page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}&userType=${param.userType}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadTeachersByCourseID(courseID: number): Observable<User[]> {
    return this.http
    .get(`${END_POINTS.users}/teachers/courses/${courseID}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }
}
