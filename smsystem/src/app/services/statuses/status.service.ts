import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, } from 'rxjs';
import {  map } from 'rxjs/operators';
import { API_END_POINTS, QueryParam } from 'app/utils';

import { ResponseWrapper, CompletionStatus, ProgressStatus } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class StatusService {

  constructor(private http: HttpClient) { }

  getCompletionStatusClass(completionStatus: string): string {
    if (CompletionStatus.IN_PROGRESS === completionStatus) {
      return 'info';
    } else if (CompletionStatus.COMPLETED === completionStatus) {
      return 'success';
    } else if (CompletionStatus.NOT_STARTED === completionStatus) {
      return 'warning';
    }
    return 'danger';
  }

  loadProgressStatuses():  Observable<ProgressStatus[]> {
    return this.http.get(`${API_END_POINTS.progressStatuses}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }
}
