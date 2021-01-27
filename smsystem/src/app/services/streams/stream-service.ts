import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {  map } from 'rxjs/operators';
import { API_END_POINTS, QueryParam } from 'app/utils';

import { ResponseWrapper, Stream } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class StreamService {
  constructor(private http: HttpClient) { }

  getStream(streamId: number): Observable<Stream> {
    return this.http
      .get(`${API_END_POINTS.streams}/${streamId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadStreamsByCapacity(capacity: number): Observable<Stream[]> {
    return this.http.get(
      `${API_END_POINTS.streams}/capacities/${capacity}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadStreams(): Observable<Stream[]> {
    return this.http.get(
        `${API_END_POINTS.streams}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getStreamPaginated(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get<any>(
        `${API_END_POINTS.streams}/paginated?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }
  createStream(data?: any): Observable<Stream> {
    return this.http.post(`${API_END_POINTS.streams}`, data).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteStream(schoolClassId: number): Observable<boolean> {
    return this.http.delete(`${API_END_POINTS.streams}/${schoolClassId}`).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateStream(data?: any): Observable<Stream> {
    return this.http.put(`${API_END_POINTS.streams}`, data).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }
}
