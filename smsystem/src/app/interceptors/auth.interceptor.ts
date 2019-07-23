import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpResponse,
  HttpRequest,
  HttpErrorResponse,
} from '@angular/common/http';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/do';

import { Observable } from 'rxjs/Observable';

import { NbAuthService, NbAuthToken, NbTokenService, NbAuthResult } from '@nebular/auth';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private token: string = '';
  private redirectUrl: string = '/account/login';

  constructor(
    private authService: NbAuthService,
    private tokenService: NbTokenService,
    protected router: Router) {

  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    this.authService.getToken().subscribe((authToken: NbAuthToken ) => {
      this.token =  authToken.getValue();
    });

    req = req.clone({
      setHeaders: {
        'Content-Type' : 'application/json; charset=utf-8',
        'Accept'       : 'application/json',
        'Authorization': this.token,
        'X-Requested-With' : 'XMLHttpRequest',
      },
    });

    return next.handle(req).do((event: HttpEvent<any>) => {
      if (event instanceof HttpResponse) {
        const resp =  event.body;
          if (resp && resp.status === 401) {
            this.authService.logout('email').subscribe((result: NbAuthResult) => {
              if (result.isSuccess()) {
                const response = result.getResponse().body;
                if (response.success) {
                  this.router.navigate([this.redirectUrl]);
                }
              }
            });
            this.tokenService.clear();
            this.router.navigate([this.redirectUrl]);          }
      }
    }, (err: any) => {
      if (err instanceof HttpErrorResponse) {
        if (err.status === 401) {
          this.tokenService.clear();
          this.router.navigate([this.redirectUrl]);
        }
      }
    });
  }
}
