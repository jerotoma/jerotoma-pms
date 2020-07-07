import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { END_POINTS, QueryParam } from 'app/utils';

import { ResponseWrapper, AcademicLevel } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class AcademicLevelService {
  constructor(private http: HttpClient) { }

  getAcademicLevel(academicLevelId: number): Observable<AcademicLevel> {
    return this.http
      .get(`${END_POINTS.academicLevels}/${academicLevelId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadAcademicLevelList(): Observable<AcademicLevel[]> {
    return this.http.get(`${END_POINTS.academicLevels}/list`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadUnAddedAcademicLevelByProgram(programId: number): Observable<AcademicLevel[]> {
    return this.http.get(`${END_POINTS.academicLevels}/programs/${programId}/unadded`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  loadAcademicLevelsByProgramId(programId: number): Observable<AcademicLevel[]> {
    return this.http.get(`${END_POINTS.academicLevels}/programs/${programId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getAcademicLevels(param: QueryParam): Observable<ResponseWrapper> {
    return this.http.get(`${END_POINTS.academicLevels}?page=${param.page}&pageSize=${param.pageSize}&orderby=${param.orderby}`)
        .pipe(map((resp: ResponseWrapper) => resp));
  }

  createAcademicLevel(data?: any): Observable<AcademicLevel> {
    return this.http.post(`${END_POINTS.academicLevels}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteAcademicLevel(academicLevelId: number): Observable<any> {
    return this.http.delete(`${END_POINTS.academicLevels}/${academicLevelId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateAcademicLevel(data?: any): Observable<AcademicLevel> {
    return this.http.put(`${END_POINTS.academicLevels}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }
}
