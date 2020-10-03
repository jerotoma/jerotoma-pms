import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { API_END_POINTS, QueryParam } from 'app/utils';

import { ResponseWrapper, StudentClassAdmission, StudentAcademicLevel } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class StudentAcademicLevelService {
  constructor(private http: HttpClient) { }

  getStudentAcademicLevel(classId: number): Observable<StudentAcademicLevel> {
    return this.http.get(`${API_END_POINTS.studentAcademicLevels}/${classId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }
  getStudentAcademicLevels(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get(`${API_END_POINTS.studentAcademicLevels}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }

  getStudentAcademicLevelByUserId(userId: number): Observable<StudentAcademicLevel> {
    return this.http.get(`${API_END_POINTS.studentAcademicLevels}/users/${userId}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getStudentAcademicLevelByStudentId(studentId: number): Observable<StudentAcademicLevel> {
    return this.http.get(`${API_END_POINTS.studentAcademicLevels}/students/${studentId}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadClassesByStudentIDAndAcademicLevelID(academicLevelId: number, studentId: number) {
    return this.http.get(`${API_END_POINTS.studentAcademicLevels}/students/${studentId}/academic-levels/${academicLevelId}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  createStudentAcademicLevel(data?: any): Observable<StudentClassAdmission> {
    return this.http.post(`${API_END_POINTS.studentAcademicLevels}`, data).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteStudentAcademicLevel(classId: number): Observable<boolean> {
    return this.http.delete<any>(`${API_END_POINTS.studentAcademicLevels}/${classId}`).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateStudentAcademicLevel(data?: any): Observable<StudentClassAdmission> {
    return this.http.put<any>(`${API_END_POINTS.studentAcademicLevels}`, data) .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }
}
