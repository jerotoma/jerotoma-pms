import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { API_END_POINTS, QueryParam } from 'app/utils';

import { ResponseWrapper, StudentClassAdmission, StudentAcademicLevel, StudentProgress } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class StudentAcademicLevelService {

  deleteStudentClass(data: { studentId: number; academicLevelId: number; academicYearId: number; jClassId: number; }): Observable<boolean> {
    return this.http.post(`${API_END_POINTS.studentAcademicLevels}/delete-student-class`, data).pipe(map((resp: ResponseWrapper) => resp.data));
  }
  constructor(private http: HttpClient) { }

  getStudentAcademicLevel(classId: number): Observable<StudentAcademicLevel> {
    return this.http.get(`${API_END_POINTS.studentAcademicLevels}/${classId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }
  getStudentAcademicLevels(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get(`${API_END_POINTS.studentAcademicLevels}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }

  getStudentAcademicLevelByUserId(userId: number): Observable<StudentAcademicLevel[]> {
    return this.http.get(`${API_END_POINTS.studentAcademicLevels}/users/${userId}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getStudentAcademicLevelByStudentId(studentId: number): Observable<StudentAcademicLevel> {
    return this.http.get(`${API_END_POINTS.studentAcademicLevels}/students/${studentId}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadStudentProgressByStudentId(studentId: number): Observable<StudentProgress> {
    return this.http.get(`${API_END_POINTS.studentAcademicLevels}/progresses/students/${studentId}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }



  loadClassesByStudentIDAndAcademicLevelID(academicLevelId: number, studentId: number) {
    return this.http.get(`${API_END_POINTS.studentAcademicLevels}/students/${studentId}/academic-levels/${academicLevelId}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  createStudentAcademicLevel(data?: any): Observable<StudentAcademicLevel> {
    return this.http.post(`${API_END_POINTS.studentAcademicLevels}`, data).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  createStudentAcademicLevelClasses(data?: any): Observable<StudentClassAdmission> {
    return this.http.post(`${API_END_POINTS.studentAcademicLevels}/classes`, data).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteStudentAcademicLevel(studentAcademicLevelId: number): Observable<boolean> {
    return this.http.delete(`${API_END_POINTS.studentAcademicLevels}/${studentAcademicLevelId}`).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteStudentAcademicLevelClass(classId: number, studentAcademicLevelId: number): Observable<boolean> {
    return this.http.delete(`${API_END_POINTS.studentAcademicLevels}/${studentAcademicLevelId}/classes/${classId}`).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateStudentAcademicLevel(data?: any): Observable<StudentAcademicLevel> {
    return this.http.put(`${API_END_POINTS.studentAcademicLevels}`, data) .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateStudentAcademicLevelClasses(data?: any): Observable<StudentClassAdmission> {
    return this.http.put(`${API_END_POINTS.studentAcademicLevels}/classes`, data) .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }
}
