import { Injectable, inject } from '@angular/core';
import { Observable, of as observableOf, throwError } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';

import { AUTH_CONSTANT } from './auth-constant';
import { LocalStorage, isObjectEmpty } from 'app/utils';
import { Token } from 'app/models/tokens/Token';


@Injectable({
  providedIn: 'root',
})
export class TokenService {
  token: string = '';
  storage: LocalStorage = null;
  jwtHelper: JwtHelperService = null;
  constructor() {
    this.init();
  }

  getToken(key: string): Observable<Token> {
    return this.processToken(this.storage.getValue(key));
  }

  getAccessToken(key: string): string {
    return this.storage.getValue(key);
  }

  setToken(key: string, value: string): void {
    this.storage.store(key, value);
  }

  removeToken(key: string, value: string): void {
    this.storage.remove(key);
  }

  clear() {
    this.storage.clear();
  }

  init() {
    if (this.storage == null) {
      this.storage = new LocalStorage();
    }
    if ( this.jwtHelper == null) {
      this.jwtHelper =  new JwtHelperService();
    }
  }

  processToken(accessToken: string): Observable<Token>  {
   return observableOf(this.jwtHelper.decodeToken(accessToken.replace(AUTH_CONSTANT.authorizationPrefix, '')));
  }

  isTokenExpired(): boolean {
    let token = this.getAccessToken(AUTH_CONSTANT.appAccessToken);
    if (token == null || token === '') {
        return false;
    }
    token = token.replace(AUTH_CONSTANT.authorizationPrefix, '');
    return this.jwtHelper.isTokenExpired(token);
  }

  isTokenExpiredAsOf(offsetSecond: number): boolean {
    let token = this.getAccessToken(AUTH_CONSTANT.appAccessToken);
    if (token == null || token === '') {
        return false;
    }
    token = token.replace(AUTH_CONSTANT.authorizationPrefix, '');
    return this.jwtHelper.isTokenExpired(token, offsetSecond);
  }

  getTokenExpirationDate(): Date | null {
    const token = this.getAccessToken(AUTH_CONSTANT.appAccessToken);
   if (token == null || token === '') {
      return null;
   }
    return this.jwtHelper.getTokenExpirationDate(token);
  }

  isTokenValid(): boolean {
    const token = this.getAccessToken(AUTH_CONSTANT.appAccessToken);
    if (token == null || token === '') {
        return false;
    }
    const payload = this.jwtHelper.decodeToken(token.replace(AUTH_CONSTANT.authorizationPrefix, ''));

   if (payload && isObjectEmpty(payload)) {
      return false;
    }
    const isExpired: boolean = this.isTokenExpiredAsOf(AUTH_CONSTANT.time_before_token_expires);

    return payload.sub && payload.scopes && payload.scopes.length > 0 && !isExpired;
  }

}
