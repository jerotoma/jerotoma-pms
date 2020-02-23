import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { END_POINTS, QueryParam } from 'app/utils';

import { ResponseWrapper, Course } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  constructor(private http: HttpClient) { }

  getCourse(courseId: number): Observable<Course> {
    return this.http
      .get<any>(`${END_POINTS.courses}/${courseId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getCoursesByAcademicYearId(academicYearId: number): Observable<Course[]> {
    return this.http.get(`${END_POINTS.courses}/academicYears/${academicYearId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadCourseList(param: QueryParam): Observable<Course[]> {
    return this.http.get(`${END_POINTS.courses}/list?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getCourses(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get(`${END_POINTS.courses}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }
  createCourse(data?: any): Observable<Course> {
    return this.http.post(`${END_POINTS.courses}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteCourse(courseId: number): Observable<any> {
    return this.http.delete(`${END_POINTS.courses}/${courseId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateCourse(data?: any): Observable<Course> {
    return this.http.put(`${END_POINTS.courses}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }
}
