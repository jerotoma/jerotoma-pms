import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {  map } from 'rxjs/operators';
import { API_END_POINTS, QueryParam } from 'app/utils';

import { ResponseWrapper, Room } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class RoomService {
  constructor(private http: HttpClient) { }

  getRoom(roomId: number): Observable<Room> {
    return this.http
      .get(`${API_END_POINTS.rooms}/${roomId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadRoomsByCapacity(capacity: number): Observable<Room[]> {
    return this.http.get(
      `${API_END_POINTS.rooms}/capacities/${capacity}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadRooms(): Observable<Room[]> {
    return this.http.get(
        `${API_END_POINTS.rooms}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getRoomPaginated(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get<any>(
        `${API_END_POINTS.rooms}/paginated?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }
  createRoom(data?: any): Observable<Room> {
    return this.http.post(`${API_END_POINTS.rooms}`, data).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteRoom(schoolClassId: number): Observable<boolean> {
    return this.http.delete(`${API_END_POINTS.rooms}/${schoolClassId}`).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateRoom(data?: any): Observable<Room> {
    return this.http.put(`${API_END_POINTS.rooms}`, data).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }
}
