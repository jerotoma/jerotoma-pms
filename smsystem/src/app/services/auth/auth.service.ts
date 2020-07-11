
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, of as observableOf, throwError } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';
import { Token } from 'app/models/tokens/token';
import { AUTH_CONSTANT } from './auth-constant';
import { TokenService } from 'app/services/auth/token.service';
import {  UserService } from 'app/services/users';
import {  LocalStorageService } from 'app/services/storage';
import { Role, USER_ROLE } from 'app/models';
import { API_END_POINTS } from 'app/utils';
import { ActivatedRouteSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  // store the URL so we can redirect after logging in
  redirectUrl: string;

  constructor(
    private storageService: LocalStorageService,
    private userService: UserService,
    private tokenService: TokenService,
    private http: HttpClient) {}

  refreshToken(): Observable<HttpResponse<any> | HttpErrorResponse > {
      return this.http
      .post<any>(`${API_END_POINTS.refreshToken}`, {refreshToken: 'ROLE_REFRESH_TOKEN'}, {observe: 'response'})
        .pipe(tap(result => {
          const resp = result;
          const token = resp.headers.get(AUTH_CONSTANT.authorization);
          const status = resp.status;
          if (status !== null && status === 200) {
            if (token !== null) {
              this.tokenService.setToken(AUTH_CONSTANT.appAccessToken, token);
              this.userService.loadCurrentUserRoles().subscribe((roles: Role[]) => {
                this.storageService.store(AUTH_CONSTANT.roles, JSON.stringify(roles));
              });
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
    return this.http.post(`${API_END_POINTS.login}`, data, {observe: 'response'})
    .pipe(map(result => {
      const resp = result;
      const token = resp.headers.get(AUTH_CONSTANT.authorization);
      const status = resp.status;
      if (status !== null && status === 200 && token) {
        this.tokenService.setToken(AUTH_CONSTANT.appAccessToken, token);
        this.userService.loadCurrentUserRoles().subscribe((roles: Role[]) => {
          this.storageService.store(AUTH_CONSTANT.roles, JSON.stringify(roles));
        });
      }
      return result;
    }));
  }

  loadCurrentUserRoles(): Role[] {
    const roles: Role[] = JSON.parse(this.storageService.getValue(AUTH_CONSTANT.roles));
    if (roles) {
      return roles;
    }
    return [ {
        id: null,
        name: USER_ROLE.ANANYMOUS,
        displayName: null,
        createdOn: new Date(),
        updatedOn: new Date(),
      },
    ];
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
    return this.http.post<any>(`${API_END_POINTS.register}`, data, {observe: 'response'})
    .pipe(tap(resp => resp, catchError(this.errorHandler)));
  }

  /**
   * Sign outs with the selected strategy
   * Removes token from the token storage
   *
   * @returns {Observable<HttpResponse<any> | HttpErrorResponse>}
   */
  logout(): Observable<HttpResponse<any> | HttpErrorResponse> {
    return this.http.post<any>(`${API_END_POINTS.logout}`, {}, {observe: 'response'})
    .pipe(map(result => {
      const resp = result;
      const status = resp.status;
      if (status !== null && status === 200) {
        this.tokenService.clear();
      }
      return result;
    }));
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
    return this.http.post<HttpResponse<any>>(`${API_END_POINTS.resetPassword}`, data, {observe: 'response'})
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
    return this.http.post<HttpResponse<any>>(`${API_END_POINTS.resetPassword}`, data, {observe: 'response'})
    .pipe(tap(resp => resp, catchError(this.errorHandler)));
  }
}
