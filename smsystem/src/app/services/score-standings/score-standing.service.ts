import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { API_END_POINTS } from 'app/utils';
import { ResponseWrapper, ScoreStanding } from 'app/models';

@Injectable({
  providedIn: 'root',
})
export class ScoreStandingService {

  constructor(private http: HttpClient) { }

  getScoreStanding(scoreStandingId: number): Observable<ScoreStanding> {
    return this.http
      .get(`${API_END_POINTS.scoreStandings}/${scoreStandingId}`)
      .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  getScoreStandings(): Observable<ScoreStanding[]> {
    return this.http.get(`${API_END_POINTS.scoreStandings}`)
        .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  createScoreStanding(data?: any): Observable<ScoreStanding> {
    return this.http.post(`${API_END_POINTS.scoreStandings}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  deleteScoreStanding(scoreStandingId: number): Observable<boolean> {
    return this.http.delete(`${API_END_POINTS.scoreStandings}/${scoreStandingId}`)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }

  updateScoreStanding(data?: any): Observable<ScoreStanding> {
    return this.http.put(`${API_END_POINTS.scoreStandings}`, data)
    .pipe(map((resp: ResponseWrapper) => resp.data));
  }
}
