import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { API_END_POINTS, QueryParam } from 'app/utils';
import { ResponseWrapper, Permission } from 'app/models';


@Injectable({
  providedIn: 'root',
})
export class PermissionService {
  constructor(private http: HttpClient) { }

  getPermission(permissionId: number): Observable<Permission> {
    return this.http
      .get(`${API_END_POINTS.permissions}/${permissionId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadPermissions(): Observable<Permission[]> {
    return this.http.get(`${API_END_POINTS.permissions}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getPermissionsPaginated(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get(`${API_END_POINTS.permissions}/paginated?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }

  createPermission(data?: any): Observable<Permission> {
    return this.http.post(`${API_END_POINTS.permissions}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deletePermission(permissionId: number): Observable<any> {
    return this.http.delete(`${API_END_POINTS.permissions}/${permissionId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updatePermission(data?: any): Observable<Permission> {
    return this.http.put(`${API_END_POINTS.permissions}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

}
