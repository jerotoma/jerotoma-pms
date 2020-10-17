import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { API_END_POINTS, QueryParam } from 'app/utils';

import { ResponseWrapper, ClassView, StudentAcademicLevelClass } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class ClassService {

  constructor(private http: HttpClient) { }

  getClass(classId: number): Observable<ClassView>  {
    return this.http.get(`${API_END_POINTS.classes}/${classId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadClassList(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get(`${API_END_POINTS.classes}/list?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }
  loadJClassesByAcademicYear(academicYearId: number): Observable<ClassView[]> {
    return this.http.get(`${API_END_POINTS.classes}/academic-years/${academicYearId}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getClasses(param: QueryParam): Observable<ResponseWrapper>  {
    return this.http.get(`${API_END_POINTS.classes}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }
  createClass(data?: any): Observable<ClassView> {
    return this.http.post<any>(`${API_END_POINTS.classes}`, data)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  postAutoGenerateClasses(data: any) {
    return this.http.post(`${API_END_POINTS.classes}/auto-generate`, data)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }
  deleteClass(classId: number): Observable<boolean> {
    return this.http.delete<any>(`${API_END_POINTS.classes}/${classId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadJClassesByParams(programId: number, academicLevelId: number, academicYearId: number): Observable<ClassView[]> {
    return this.http.get(`${API_END_POINTS.classes}/programs/${programId}/academic-levels/${academicLevelId}/academic-years/${academicYearId}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadClassesByStudentIDAndAcademicLevelID(studentId: number, academicLevelId: number): Observable<ClassView[]> {
    return this.http.get(`${API_END_POINTS.classes}/students/${studentId}/academic-levels/${academicLevelId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateClass(data?: any): Observable<ClassView> {
    return this.http.put(`${API_END_POINTS.classes}`, data).pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadUnregisteredClassesByStudent(studentId: number, academicLevelId: number, academicYearId: number): Observable<ClassView[]> {
    return this.http.get(`${API_END_POINTS.classes}/students/${studentId}/academic-levels/${academicLevelId}/academic-years/${academicYearId}/unregistered`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadStudentRegisteredClasses(studentId: number, academicLevelId: number, academicYearId: number): Observable<ClassView[]> {
    return this.http.get(`${API_END_POINTS.classes}/students/${studentId}/academic-levels/${academicLevelId}/academic-years/${academicYearId}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadStudentClassesByAcademicYear(academicYearId: number, studentId: number) {
    return this.http.get(`${API_END_POINTS.classes}/academic-years/${academicYearId}/students/${studentId}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadClassesByUserId(userId: number): Observable<ClassView[]> {
    return this.http.get(`${API_END_POINTS.classes}/users/${userId}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadAllStudentAcademicLevelsClassList(userId: number): Observable<StudentAcademicLevelClass[]> {
    return this.http.get(`${API_END_POINTS.classes}/users/${userId}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }
  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }
}
