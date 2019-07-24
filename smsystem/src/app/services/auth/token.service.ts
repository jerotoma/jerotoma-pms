import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {  delay, map, catchError, retry } from 'rxjs/operators';
import { Data } from '@angular/router';

import { User } from '../../models/users';
import { END_POINTS, QueryParam } from '../../utils';
import { NbTokenService, NbTokenStorage} from '@nebular/auth';

@Injectable({providedIn: 'root'})
export class TokenService extends NbTokenService {

  constructor(tokenStorage: NbTokenStorage) {
    super(tokenStorage);
  }

}
