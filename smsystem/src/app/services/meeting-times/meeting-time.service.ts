import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { END_POINTS, QueryParam } from 'app/utils';
import { ResponseWrapper, MeetingTime, Time } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class MeetingTimeService {
  constructor(private http: HttpClient) { }

  getMeetingTime(meetingTimeId: number): Observable<MeetingTime> {
    return this.http
      .get(`${END_POINTS.meetingTimes}/${meetingTimeId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadMeetingTimes(): Observable<MeetingTime[]> {
    return this.http.get(`${END_POINTS.meetingTimes}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getMeetingTimesPaginated(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get(`${END_POINTS.meetingTimes}/paginated?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }

  createMeetingTime(data?: any): Observable<MeetingTime> {
    return this.http.post(`${END_POINTS.meetingTimes}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteMeetingTime(meetingTimeId: number): Observable<any> {
    return this.http.delete(`${END_POINTS.meetingTimes}/${meetingTimeId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateMeetingTime(data?: any): Observable<MeetingTime> {
    return this.http.put(`${END_POINTS.meetingTimes}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }
}
