import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { NbTokenService, NbAuthService } from '@nebular/auth';
import { END_POINTS } from '../../utils';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(tokenService: NbTokenService, private http: HttpClient) {}

  refreshToken(data: any): Observable<HttpResponse<any>> {
      return this.http.post<HttpResponse<any>>(`${END_POINTS.refreshToken}`, data)
                      .pipe(tap(resp => console.log('response', resp)), catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server error');
  }

}
