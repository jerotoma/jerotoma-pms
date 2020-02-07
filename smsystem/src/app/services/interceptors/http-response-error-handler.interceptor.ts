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
import { Observable, of } from 'rxjs';
import { catchError, tap} from 'rxjs/operators';
import { AuthService } from 'app/services/auth';
import { ErrorDialogService } from 'app/services/modals';
import { HTTP_STATUS_CODES } from 'app/utils';

@Injectable()
export class HttpResponseErrorInterceptor implements HttpInterceptor {

  constructor(
    private authService: AuthService,
    private errorDialogService: ErrorDialogService,
    protected router: Router) {

  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    // Nothing to intecept for outgoing request.

    // Intercept responses for errors
    return next.handle(req).pipe(
      tap(event => {
        if (event instanceof HttpResponse) {
          if (event.body && event.status === HTTP_STATUS_CODES.CODE_200.id) {
            this.logSuccessResponse(HTTP_STATUS_CODES.CODE_200.message);
          }
        }
      }),
      catchError((err: HttpEvent<any>) => {
        if (err instanceof HttpErrorResponse) {
            switch (err.status) {
              case HTTP_STATUS_CODES.CODE_400.id:
                this.logErrorResponse(HTTP_STATUS_CODES.CODE_400.message);
                break;
              case HTTP_STATUS_CODES.CODE_401.id:
                this.errorDialogService.openDialog(err.error.message);
                break;
              case HTTP_STATUS_CODES.CODE_402.id:
                this.logErrorResponse(HTTP_STATUS_CODES.CODE_402.message);
                break;
              case HTTP_STATUS_CODES.CODE_403.id:
                this.logErrorResponse(HTTP_STATUS_CODES.CODE_403.message);
                break;
              case HTTP_STATUS_CODES.CODE_404.id:
                this.logErrorResponse(HTTP_STATUS_CODES.CODE_404.message);
                break;
              case HTTP_STATUS_CODES.CODE_405.id:
                this.logErrorResponse(HTTP_STATUS_CODES.CODE_405.message);
                this.errorDialogService.popup(this.router.url, err.error.message);
                break;
                default:
                  this.logErrorResponse(HTTP_STATUS_CODES.CODE_404.message);
                break;

            }
        } else { // Non Http Error Response
          this.logErrorResponse('Non HttpErrorResponse caught in Http error handler');
        }
        return of(err);
      }),
    );

  }
  private logErrorResponse(errorMessage: any) {
    window.console.error(this, errorMessage);
  }

  private logSuccessResponse(message: any) {
    window.console.log(this, message);
  }



}
