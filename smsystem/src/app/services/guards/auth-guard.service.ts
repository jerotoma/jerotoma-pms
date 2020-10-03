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
import { tap,} from 'rxjs/operators';

import { FRONTEND_ENDPOINTS, MESSAGE } from 'app/utils';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate, CanActivateChild {

  isAuthenticated: boolean = false;

  constructor(
    private authService: AuthService,
    private modalService: ModalService,
    private userService: UserService,
    private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.checkAuthAndAuthorization(route, state);
  }
  canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.canActivate(route, state);
  }

  checkAuthAndAuthorization(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    // Store the attempted URL for redirecting
    this.authService.redirectUrl = state.url;;
    if (!this.authService.isAuthenticated()) {
        this.router.navigate([FRONTEND_ENDPOINTS.login.path]);
        return observableOf(false);
    }
    return this.checkRoles(route, state);
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
      return observableOf(false);
    }
    return observableOf(true);
  }
}
