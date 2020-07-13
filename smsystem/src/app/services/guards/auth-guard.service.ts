import { Injectable } from '@angular/core';
import {
  CanActivate, Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  CanActivateChild,
  NavigationExtras,
  CanLoad, Route,
} from '@angular/router';
import { AuthService } from 'app/services/auth';
import { UserService } from 'app/services/users';
import { ModalService } from 'app/services/modals';
import { Role, USER_ROLE } from 'app/models/securities';
import { Observable, of as observableOf } from 'rxjs';
import { tap } from 'rxjs/operators';

import { FRONTEND_ENDPOINTS, MESSAGE } from 'app/utils';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate, CanActivateChild, CanLoad {

  isAuthenticated: boolean = false;

  constructor(
    private authService: AuthService,
    private modalService: ModalService,
    private userService: UserService,
    private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    const url: string = state.url;
    return this.checkLogin(url) && this.checkRoles(route, state);
  }
  canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.canActivate(route, state);
  }

  canLoad(route: Route): Observable<boolean> {
     return this.checkLogin(`/${route.path}`);
  }

  checkLogin(url: string): Observable<boolean> {
    // Store the attempted URL for redirecting
    this.authService.redirectUrl = url;
    return this.authService.isAuthenticated().pipe(
      tap(authenticated => {
        this.isAuthenticated = authenticated;
        if (!authenticated) {
          this.router.navigate([FRONTEND_ENDPOINTS.login.path]);
        }
      }),
    );
  }

  checkRoles(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    const roles: USER_ROLE[] = this.authService.loadCurrentUserRoles();
    const navigationExtras: NavigationExtras = {
      queryParams: {returnUrl: state.url },
    };
    // check if route is restricted by role
    if (route.data.roles && roles) {
      for (let i = 0; i < roles.length; i++) {
        if (route.data.roles.indexOf(roles[i]) !== -1) {
          return observableOf(true);
        }
      }
      // role not authorised so redirect to home page
      this.modalService.openSnackBar(MESSAGE.ERROR.notAuthorized, 'danger');
      navigationExtras.replaceUrl = false;
      this.router.navigate([FRONTEND_ENDPOINTS.dashboard.path], navigationExtras);
    }  else {
      this.router.navigate([FRONTEND_ENDPOINTS.login.path], navigationExtras);
    }
    return observableOf(false);
  }
}
