import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { API_END_POINTS, QueryParam, WEEK_DAY } from 'app/utils';
import { ResponseWrapper, Role } from 'app/models';


@Injectable({
  providedIn: 'root',
})
export class RoleService {

  constructor(private http: HttpClient) { }

  getRole(roleId: number): Observable<Role> {
    return this.http
      .get(`${API_END_POINTS.roles}/${roleId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }


  assignationRole(data: any): Observable<any> {
    return this.http.post(`${API_END_POINTS.roles}/assignations`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadRoles(): Observable<Role[]> {
    return this.http.get(`${API_END_POINTS.roles}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getRolesPaginated(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get(`${API_END_POINTS.roles}/paginated?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }

  createRole(data?: any): Observable<Role> {
    return this.http.post(`${API_END_POINTS.roles}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteRole(roleId: number): Observable<any> {
    return this.http.delete(`${API_END_POINTS.roles}/${roleId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateRole(data?: any): Observable<Role> {
    return this.http.put(`${API_END_POINTS.roles}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

}
