import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {  map } from 'rxjs/operators';
import { END_POINTS, QueryParam } from 'app/utils';

import { ResponseWrapper, Room } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class RoomService {
  constructor(private http: HttpClient) { }

  getRoom(roomId: number): Observable<Room> {
    return this.http
      .get(`${END_POINTS.rooms}/${roomId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadRoomList(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get<any>(
        `${END_POINTS.rooms}/list?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }

  getRooms(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get<any>(
        `${END_POINTS.rooms}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }
  createRoom(data?: any): Observable<Room> {
    return this.http.post(`${END_POINTS.rooms}`, data).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteRoom(schoolClassId: number): Observable<boolean> {
    return this.http.delete(`${END_POINTS.rooms}/${schoolClassId}`).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateRoom(data?: any): Observable<Room> {
    return this.http.put(`${END_POINTS.rooms}`, data).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }
}
