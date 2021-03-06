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
import { catchError, tap} from 'rxjs/operators';
import { AuthService } from 'app/services/auth';
import { ModalService } from 'app/services/modals';
import { HTTP_STATUS_CODES } from 'app/utils';

@Injectable()
export class HttpResponseErrorInterceptor implements HttpInterceptor {

   HTTP_400_SERIES_ERROR_CODES: number[] = [
    HTTP_STATUS_CODES.CODE_400.id,
    HTTP_STATUS_CODES.CODE_401.id,
    HTTP_STATUS_CODES.CODE_402.id,
    HTTP_STATUS_CODES.CODE_403.id,
    HTTP_STATUS_CODES.CODE_404.id,
    HTTP_STATUS_CODES.CODE_405.id,
  ];

  constructor(
    private authService: AuthService,
    private errorDialogService: ModalService,
    protected router: Router) {

  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    // Nothing to intecept for outgoing request.

    // Intercept responses for errors
    return next.handle(req).pipe(
      tap(event => {
        if (event instanceof HttpResponse) {
          if (event.body && event.status === HTTP_STATUS_CODES.CODE_200.id) {
            // this.errorDialogService.logSuccessResponse(HTTP_STATUS_CODES.CODE_200.message);
          }
        }
      }),
      catchError((err: HttpEvent<any>) => {
        if (err instanceof HttpErrorResponse) {
          if (this.HTTP_400_SERIES_ERROR_CODES.indexOf(err.status) !== -1) {
              this.errorDialogService.openSnackBar(err.error.message, 'danger');
          } else {
            this.errorDialogService.openSnackBar(err.error.message, 'danger');
          }
        }
        return throwError(err);
      }),
    );

  }

}
