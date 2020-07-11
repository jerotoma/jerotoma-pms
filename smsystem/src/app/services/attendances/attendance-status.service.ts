import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, map } from 'rxjs/operators';
import { API_END_POINTS, QueryParam } from 'app/utils';

import { AcademicYear, ResponseWrapper, AttendanceStatus} from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class AttendanceStatusService {

  loadAttendancesPaginated(param: QueryParam) {
    return this.http.get(`${API_END_POINTS.attendances}/statuses/paginated?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
      .pipe(map((resp: ResponseWrapper) => resp));
  }

  constructor(private http: HttpClient) { }

  createAttendanceStatus(attendanceStatus: AttendanceStatus): Observable<AttendanceStatus> {
    return this.http.post(`${API_END_POINTS.attendances}/statuses`, attendanceStatus)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateAttendanceStatus(attendanceStatus: AttendanceStatus) {
    return this.http.put(`${API_END_POINTS.attendances}/statuses`, attendanceStatus)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadAttendanceStatuses(): Observable<AttendanceStatus[]> {
    return this.http.get(`${API_END_POINTS.attendances}/statuses`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

}
