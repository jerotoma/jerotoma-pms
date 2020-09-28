import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';

import { User, ResponseWrapper} from 'app/models';
import { API_END_POINTS } from 'app/utils';

@Injectable({
  providedIn: 'root',
})
export class StudentAcademicLevelService {
  constructor(private http: HttpClient) {}

  loadStudentsWhoAreUnenrolledAndQualifiedForThisProgramAndAcademicLevel(programId: number, academicLevelId: number):  Observable<User[]> {
    return this.http
    .get(`${API_END_POINTS.studentAcademicLevel}/unenrolled/programs/${programId}/academic-levels/${academicLevelId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }
}
