import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, map } from 'rxjs/operators';
import { API_END_POINTS, QueryParam } from 'app/utils';

import { AttendanceReport, ResponseWrapper, ClassAttendance } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class AttendanceReportService {
  constructor(private http: HttpClient) { }

  getAttendanceReports(studentId: number, academicLevelId: number): Observable<AttendanceReport[]> {
    return this.http.get(`${API_END_POINTS.attendanceReports}/students/${studentId}/academic-levels/${academicLevelId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getAttendanceReportDetails(classId: number, studentId: number, academicLevelId: number): Observable<AttendanceReport[]> {
    return this.http.get(`${API_END_POINTS.attendanceReports}/classes/${classId}/students/${studentId}/academic-levels/${academicLevelId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadStudentClassAttendanceReportByClass(classId: number, studentId: number, academicLevelId: number): Observable<ClassAttendance[]> {
    return this.http.get(`${API_END_POINTS.attendanceReports}/classes/${classId}/students/${studentId}/academic-levels/${academicLevelId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }


}
