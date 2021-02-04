import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { API_END_POINTS } from 'app/utils';

import { ResponseWrapper, StudentClassesProgress, StudentAcademicLevelsProgress } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class StudentProgressService {
  constructor(private http: HttpClient) { }

  findStudentClassesProgressByStudentId(studentId: number): Observable<StudentClassesProgress> {
    return this.http.get(`${API_END_POINTS.studentProgresses}/classes/students/${studentId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  findStudentAcademicLevelsProgressByStudentId(studentId: number): Observable<StudentAcademicLevelsProgress> {
    return this.http.get(`${API_END_POINTS.studentProgresses}/academic-levels/students/${studentId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateStudentAcademicLevelsProgress(data: any): Observable<StudentAcademicLevelsProgress> {
    return this.http.post(`${API_END_POINTS.studentProgresses}/academic-levels/students`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }
}
