
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, of as observableOf, throwError } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';
import { Token } from '../../models/tokens/Token';
import { AUTH_CONSTANT } from './auth-constant';
import { TokenService } from './token.service';
import { END_POINTS } from '../../utils';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  // store the URL so we can redirect after logging in
  redirectUrl: string;

  constructor(private tokenService: TokenService, private http: HttpClient) {}

  refreshToken(): Observable<HttpResponse<any> | HttpErrorResponse > {
      return this.http
      .post<any>(`${END_POINTS.refreshToken}`, {refreshToken: 'ROLE_REFRESH_TOKEN'}, {observe: 'response'})
                      .pipe(tap(result => {
                        const resp = result;
                        const token = resp.headers.get(AUTH_CONSTANT.authorization);
                        const status = resp.status;
                        if (status !== null && status === 200) {
                          if (token !== null) {
                            this.tokenService.setToken(AUTH_CONSTANT.appAccessToken, token);
                          }
                        }
                      }));
  }

  errorHandler(error: HttpErrorResponse): Observable<HttpErrorResponse> {
    return throwError(error);
  }

    /**
   * Retrieves current authenticated token stored
   * @returns {Observable<any>}
   */
  getToken(): Observable<Token> {
    return this.tokenService.getToken(`${AUTH_CONSTANT.appAccessToken}`);
  }

  getAccessToken(): string {
    const token = this.tokenService.getAccessToken(`${AUTH_CONSTANT.appAccessToken}`);
    return token === null ? '' : token;
  }
  /**
   * Returns true if auth token is present in the token storage
   * @returns {Observable<boolean>}
   */
  isAuthenticated(): Observable<boolean> {
    const isAuth = this.tokenService.isTokenValid();
    if (isAuth) {
      return observableOf(true);
    }
    return observableOf(this.tokenService.isTokenValid());
  }

  /**
   * Authenticates with the selected strategy
   * Stores received token in the token storage
   *
   * Example:
   * authenticate({email: 'email@example.com', password: 'test'})
   *
   * @param data
   * @returns {Observable<HttpResponse<any> | HttpErrorResponse>}
   */
  authenticate(data?: any): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.post<any>(`${END_POINTS.login}`, data, {observe: 'response'})
    .pipe(map(result => {
      const resp = result;
      const token = resp.headers.get(AUTH_CONSTANT.authorization);
      const status = resp.status;
      if (status !== null && status === 200 && token) {
        this.tokenService.setToken(AUTH_CONSTANT.appAccessToken, token);
      }
      return result;
    }));
  }

  /**
   * Registers with the selected strategy
   * Stores received token in the token storage
   *
   * Example:
   * register('email', {email: 'email@example.com', name: 'Some Name', password: 'test'})
   *
   * @param strategyName
   * @param data
   * @returns {Observable<HttpResponse<any> | HttpErrorResponse>}
   */
  register(data?: any): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.post<any>(`${END_POINTS.register}`, data, {observe: 'response'})
    .pipe(tap(resp => resp, catchError(this.errorHandler)));
  }

  /**
   * Sign outs with the selected strategy
   * Removes token from the token storage
   *
   * @returns {Observable<HttpResponse<any> | HttpErrorResponse>}
   */
  logout(): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.post<any>(`${END_POINTS.logout}`, {}, {observe: 'response'})
    .pipe(tap(resp => resp, catchError(this.errorHandler)));
  }

  /**
   * Sends forgot password request to the selected strategy
   *
   * Example:
   * requestPassword('email', {email: 'email@example.com'})
   *
   * @param data
   * @returns {Observable<HttpResponse<any> | HttpErrorResponse>}
   */
  requestPassword(data?: any): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.post<HttpResponse<any>>(`${END_POINTS.resetPassword}`, data, {observe: 'response'})
    .pipe(tap(resp => resp, catchError(this.errorHandler)));
  }

  /**
   * Tries to reset password with the selected strategy
   *
   * Example:
   * resetPassword('email', {newPassword: 'test'})
   *
   * @param strategyName
   * @param data
   * @returns {Observable<HttpResponse<any> | HttpErrorResponse>}
   */
  resetPassword(data?: any): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.post<HttpResponse<any>>(`${END_POINTS.resetPassword}`, data, {observe: 'response'})
    .pipe(tap(resp => resp, catchError(this.errorHandler)));
  }
}
