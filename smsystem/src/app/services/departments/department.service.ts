import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { API_END_POINTS, QueryParam } from 'app/utils';

import { ResponseWrapper, Department } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class DepartmentService {

  constructor(private http: HttpClient) { }

  getDepartment(departmentId: number): Observable<Department> {
    return this.http
      .get(`${API_END_POINTS.departments}/${departmentId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadDepartmentList(): Observable<Department[]> {
    return this.http.get(`${API_END_POINTS.departments}/list`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getDepartments(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get(`${API_END_POINTS.departments}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }

  createDepartment(data?: any): Observable<Department> {
    return this.http.post(`${API_END_POINTS.departments}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteDepartment(departmentId: number): Observable<any> {
    return this.http.delete(`${API_END_POINTS.departments}/${departmentId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateDepartment(data?: any): Observable<Department> {
    return this.http.put(`${API_END_POINTS.departments}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }
}
