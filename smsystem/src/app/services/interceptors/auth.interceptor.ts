import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpResponse,
  HttpRequest,
  HttpErrorResponse,
} from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError, switchMap, take, filter} from 'rxjs/operators';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import {
  AuthService,
  TokenService,
  AUTH_CONSTANT,
} from '../auth';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private token: string = '';
  private redirectUrl: string = '/account/login';
  private isRefreshing: boolean = false;

  // Refresh Token Subject tracks the current token, or is null if no token is currently
    // available (e.g. refresh pending).
    private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

  constructor(
    private authService: AuthService,
    protected router: Router) {

  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.token = this.authService.getAccessToken();
    const url = req.url;
    return next.handle(this.addHeaders(req, this.token)).pipe(catchError( error => {
      if (error instanceof HttpErrorResponse && error.status === 401) {
        return this.handle401Error(req, next);
      }
      return throwError(error);
    }));
  }

  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.refreshTokenSubject.next(null);
      return this.authService.refreshToken().pipe(
        switchMap((resp: HttpResponse<any>) => {
          this.isRefreshing = false;
          const token = resp.headers.get(AUTH_CONSTANT.authorization);
          this.refreshTokenSubject.next(token);
          return next.handle(this.addHeaders(request, token));
        }));
    } else {
      return this.refreshTokenSubject.pipe(
        filter(token => token != null),
        take(1),
        switchMap(jwt => {
          return next.handle(this.addHeaders(request, jwt));
        }));
    }
  }

  addHeaders(req: HttpRequest<any>, token: string) {
    return req.clone({
      setHeaders: {
        'Accept'       : 'application/json',
        'Authorization': token,
        'X-Requested-With' : 'XMLHttpRequest',
      },
    });
  }

  /**
   * .do((event: HttpEvent<any>) => {
      if (event instanceof HttpResponse) {
        const resp =  event.body;
         if (url.match(END_POINTS.dashboard) && resp && resp.status === 401) {
           return this.handle401Error(req, next);
          }
      }
    }, (err: any) => {
      if (err instanceof HttpErrorResponse) {
        if (url.match(END_POINTS.dashboard) && err.status === 401) {
          return this.handle401Error(req, next);
        }
      }
    });
  */
}
