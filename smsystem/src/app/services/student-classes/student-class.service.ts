import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { API_END_POINTS, QueryParam } from 'app/utils';

import { ResponseWrapper, StudentClass, AcademicLevelPrerequisite } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class StudentClassService {

  constructor(private http: HttpClient) { }

  getStudentClassProgress(StudentClassProgressId: number): Observable<StudentClass> {
    return this.http
      .get(`${API_END_POINTS.studentClasses}/${StudentClassProgressId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadStudentClassProgresses(): Observable<StudentClass[]> {
    return this.http.get(`${API_END_POINTS.studentClasses}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getStudentClassProgresses(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get(`${API_END_POINTS.studentClasses}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }

  createStudentClassProgress(data?: any): Observable<ResponseWrapper> {
    return this.http.post(`${API_END_POINTS.studentClasses}`, data)
    .pipe(map((resp: ResponseWrapper) => resp));
  }

  loadStudentsClassProgressByClassId(classId: number): Observable<StudentClass[]> {
    return this.http.get(`${API_END_POINTS.studentClasses}/classes/${classId}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteStudentClassProgress(studentClassId: number): Observable<any> {
    return this.http.delete(`${API_END_POINTS.studentClasses}/${studentClassId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateStudentClassProgress(data?: StudentClass): Observable<StudentClass> {
    return this.http.put(`${API_END_POINTS.studentClasses}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }
}
