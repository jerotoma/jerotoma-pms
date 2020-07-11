import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { API_END_POINTS, QueryParam } from 'app/utils';
import { ResponseWrapper, SystemConfig } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class SystemConfigService {

  constructor(private http: HttpClient) { }

  getSystemConfig(systemConfigId: number): Observable<SystemConfig> {
    return this.http.get(`${API_END_POINTS.systemConfigs}/${systemConfigId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }
  findSystemConfigByKey(systemConfigKey: string): Observable<SystemConfig> {
    return this.http.get(`${API_END_POINTS.systemConfigs}/keys/?key=${systemConfigKey}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }
  getSystemConfigByKey(systemConfigKey: string): Observable<SystemConfig> {
    return this.http.get(`${API_END_POINTS.pubSystemConfigs}/keys/?key=${systemConfigKey}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadSystemConfigList(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get<any>(
        `${API_END_POINTS.systemConfigs}/list?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }

  getSystemConfigs(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get<any>(`${API_END_POINTS.systemConfigs}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
    .pipe(map((resp: ResponseWrapper) => resp));
  }
  createSystemConfig(data?: any): Observable<SystemConfig> {
    return this.http.post(`${API_END_POINTS.systemConfigs}`, data).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteSystemConfig(systemConfigId: number): Observable<boolean> {
    return this.http.delete<any>(`${API_END_POINTS.systemConfigs}/${systemConfigId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateSystemConfig(data?: any): Observable<SystemConfig> {
    return this.http.put(`${API_END_POINTS.systemConfigs}`, data)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }
}
