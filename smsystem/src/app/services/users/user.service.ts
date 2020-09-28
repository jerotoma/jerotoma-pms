import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';

import { User, ResponseWrapper, Role, USER_ROLE } from 'app/models';
import { API_END_POINTS, QueryParam, USER_TYPE } from 'app/utils';

@Injectable({
  providedIn: 'root',
})
export class UserService {

  private currentUserRolesSubject: BehaviorSubject<USER_ROLE[]>;
  public currentUserRoles: Observable<USER_ROLE[]>;

  constructor(private http: HttpClient) {}

  loadCurrentUserRoles(): Observable<USER_ROLE[]> {
    return this.http
      .get(`${API_END_POINTS.users}/currentUser/roles`)
      .pipe(map((resp: ResponseWrapper) => {
        this.currentUserRolesSubject = new BehaviorSubject<USER_ROLE[]>(resp.data);
        return resp.data;
      }));
  }

  public get loggedInUserRoles(): USER_ROLE[] {
    return this.currentUserRolesSubject.value;
  }

  getCurrentUser(): Observable<User> {
    return this.http
      .get(`${API_END_POINTS.users}/currentUser`).
      pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getUser(userId: number): Observable<User> {
    return this.http
      .get(`${API_END_POINTS.users}/${userId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadUser(userId: number, userType: string): Observable<User> {
    switch (userType) {
        case USER_TYPE.PARENT:
          userType = 'parents';
        break;
        case USER_TYPE.STAFF:
          userType = 'staffs';
        break;
        case USER_TYPE.TEACHER:
          userType = 'teachers';
        break;
        case USER_TYPE.STUDENT:
          userType = 'students';
        break;
    }
    return this.http
    .get(`${API_END_POINTS.users}/${userType}/${userId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadStudentsByProgramAndAcademicLevelIDs(academicLevelId: number, programId: number):  Observable<User[]> {
    return this.http
    .get(`${API_END_POINTS.users}/programs/${programId}/academic-levels/${academicLevelId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadUserDetails(username: string): Observable<User> {
    return this.http
    .post(`${API_END_POINTS.users}/profile`, {username: username})
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  load(param: QueryParam): Observable<any> {
    return this.http
      .get(`${API_END_POINTS.users}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadUsers(param: QueryParam): Observable<any> {
    return this.http
      .get(`${API_END_POINTS.users}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}&userType=${param.userType}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  addUser(data: any): Observable<ResponseWrapper>  {
    return this.http.post(`${API_END_POINTS.users}`, data)
    .pipe(map((resp: ResponseWrapper) => resp));
  }

  updateUser(data: any): Observable<ResponseWrapper>  {
    return this.http.put(`${API_END_POINTS.users}`, data)
    .pipe(map((resp: ResponseWrapper) => resp));
  }

  deleteUser(userId: number, userType: string): Observable<User> {
    return this.http.delete(`${API_END_POINTS.users}/${userId}?userType=${userType}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  search(param: QueryParam): Observable<User[]> {
    return this.http
    .get(`${API_END_POINTS.users}/search?searchTerm=${param.search}&page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}&userType=${param.userType.toLowerCase()}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadTeachersByCourseID(courseID: number): Observable<User[]> {
    return this.http
    .get(`${API_END_POINTS.users}/teachers/courses/${courseID}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }
}
