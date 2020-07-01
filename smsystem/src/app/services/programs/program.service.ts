import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { END_POINTS, QueryParam } from 'app/utils';

import { ResponseWrapper, Program } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class ProgramService {
    constructor(private http: HttpClient) { }

  getProgram(programId: number): Observable<Program> {
    return this.http
      .get(`${END_POINTS.programs}/${programId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadProgramList(): Observable<Program[]> {
    return this.http.get(`${END_POINTS.programs}/list`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getPrograms(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get(`${END_POINTS.programs}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }

  createProgram(data?: any): Observable<Program> {
    return this.http.post(`${END_POINTS.programs}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteProgram(programId: number): Observable<any> {
    return this.http.delete(`${END_POINTS.programs}/${programId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  removeAcademicLevelFromProgram(programId: number, academicLeveId: number): Observable<boolean> {
    return this.http.delete(`${END_POINTS.programs}/${programId}/academic-levels/${academicLeveId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateProgram(data?: any): Observable<Program> {
    return this.http.put(`${END_POINTS.programs}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }
}
