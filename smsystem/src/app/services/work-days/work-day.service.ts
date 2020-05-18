import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { END_POINTS, QueryParam } from 'app/utils';
import { ResponseWrapper, WorkDay } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class WorkDayService {

  constructor(private http: HttpClient) { }

  getWorkDay(workDayId: number): Observable<WorkDay> {
    return this.http
      .get(`${END_POINTS.workDays}/${workDayId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadWorkDays(): Observable<WorkDay[]> {
    return this.http.get(`${END_POINTS.workDays}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getWorkDaysPaginated(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get(`${END_POINTS.workDays}/paginated?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }

  createWorkDay(data?: any): Observable<WorkDay> {
    return this.http.post(`${END_POINTS.workDays}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteWorkDay(workDayId: number): Observable<any> {
    return this.http.delete(`${END_POINTS.workDays}/${workDayId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateWorkDay(data?: any): Observable<WorkDay> {
    return this.http.put(`${END_POINTS.workDays}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }
}
