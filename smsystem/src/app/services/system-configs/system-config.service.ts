import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse, HttpEvent } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { END_POINTS, QueryParam } from 'app/utils';

@Injectable({
  providedIn: 'root',
})
export class SystemConfigService {

  constructor(private http: HttpClient) { }

  getSystemConfig(systemConfigId: number): Observable<any> {
    return this.http
      .get<any>(`${END_POINTS.systemConfigs}/${systemConfigId}`)
      .pipe(retry(3), catchError(this.errorHandler));
  }
  getSystemConfigByKey(systemConfigKey: string): Observable<any> {
    return this.http
      .get<any>(`${END_POINTS.systemConfigs}/keys/?key=${systemConfigKey}`)
      .pipe(retry(3), catchError(this.errorHandler));
  }

  loadSystemConfigList(param: QueryParam): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.get<any>(
        `${END_POINTS.systemConfigs}/list?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`,
        {observe: 'response'})
      .pipe(retry(3), catchError(this.errorHandler));
  }

  getSystemConfigs(param: QueryParam): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.get<any>(
        `${END_POINTS.systemConfigs}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`,
        {observe: 'response'})
      .pipe(retry(3), catchError(this.errorHandler));
  }
  createSystemConfig(data?: any): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.post<any>(`${END_POINTS.systemConfigs}`, data, {observe: 'response'});
  }

  deleteSystemConfig(systemConfigId: number): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.delete<any>(`${END_POINTS.systemConfigs}/${systemConfigId}`, {observe: 'response'});
  }

  updateSystemConfig(data?: any): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.put<any>(`${END_POINTS.systemConfigs}`, data, {observe: 'response'});
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }
}
