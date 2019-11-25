import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse, HttpEvent } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { END_POINTS, QueryParam } from 'app/utils';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {

  constructor(private http: HttpClient) { }

  getCurrentSystemTheme(): Observable<any> {
    return this.http.get<any>(`${END_POINTS.pubThemes}`)
      .pipe(catchError(this.errorHandler));
  }
  getUserAndSystemThemes(): Observable<any> {
    return this.http.get<any>(`${END_POINTS.themes}`)
      .pipe(catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }
}
