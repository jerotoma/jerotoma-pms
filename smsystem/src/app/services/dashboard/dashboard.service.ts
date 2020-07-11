import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { API_END_POINTS, QueryParam } from 'app/utils';

import { ResponseWrapper, DashboardCounter } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {

  constructor(private http: HttpClient) { }


  getDashboardCount(): Observable<DashboardCounter> {
    return this.http
      .get(`${API_END_POINTS.restDashboard}/counters`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadDashboardList(): Observable<DashboardCounter[]> {
    return this.http.get(`${API_END_POINTS.restDashboard}/list`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getDashboards(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get(`${API_END_POINTS.restDashboard}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }


}
