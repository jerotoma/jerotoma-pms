import { Component, OnDestroy, OnInit } from '@angular/core';
import { NbAuthService } from '@nebular/auth';
import { NbMediaBreakpointsService, NbMenuService, NbSidebarService, NbThemeService } from '@nebular/theme';

import {User, SystemConfig, UserPreference, Theme } from 'app/models';
import {UserService, SystemConfigService, ThemeService } from 'app/services';


import { LayoutService } from 'app/services';
import { map, takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { APP_CONSTANTS, THEMES, END_POINTS } from 'app/utils';
import { USER_DROPDOWN_ITEMS } from './header-menu';

@Component({
  selector: 'app-header',
  styleUrls: ['./header.component.scss'],
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit, OnDestroy {

  private destroy$: Subject<void> = new Subject<void>();
  userPictureOnly: boolean = false;
  overrideUserTheme: boolean = false;
  user: User;
  themes = THEMES;
  baseURL: string = END_POINTS.baseURL;
  mTheme: Theme = null;
  currentTheme = 'default';
  systemTheme: string = APP_CONSTANTS.currentTheme;
  userMenu = USER_DROPDOWN_ITEMS;
  systemConfig: SystemConfig = null;
  overrideSystemConfig: SystemConfig = null;
  userPreference: UserPreference = null;

  constructor(
    private userService: UserService,
    private authService: NbAuthService,
    private sidebarService: NbSidebarService,
    private menuService: NbMenuService,
    private mThemeService: ThemeService,
    private themeService: NbThemeService,
    private layoutService: LayoutService,
    private breakpointService: NbMediaBreakpointsService) {
  }

  ngOnInit() {
    this.currentTheme = this.themeService.currentTheme;
    this.loadCurrentUser();
    const { xl } = this.breakpointService.getBreakpointsMap();
    this.themeService.onMediaQueryChange()
      .pipe(
        map(([, currentBreakpoint]) => currentBreakpoint.width < xl),
        takeUntil(this.destroy$),
      )
      .subscribe((isLessThanXl: boolean) => this.userPictureOnly = isLessThanXl);

    this.menuService.onItemClick().subscribe(( event ) => {
        this.onItemSelection(event.item.title);
      });
      this.loadCurrentTheme();
  }

  loadCurrentUser() {
    if (this.authService.isAuthenticatedOrRefresh()) {
      this.userService.getCurrentUser().subscribe((user: User) => {
        this.user = user;
        // console.log(user);
      });
    }
  }
  onItemSelection(title: string) {
    if ( title === 'Log out' ) {
      // Do something on Log out
      // window.console.log('Log out Clicked');
    } else if ( title === 'Profile' ) {
      // Do something on Profile
      // window.console.log('Profile Clicked');
    }
  }
  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  changeTheme(themeName: string) {
    this.themeService.changeTheme(themeName);
  }

  toggleSidebar(): boolean {
    this.sidebarService.toggle(true, 'menu-sidebar');
    this.layoutService.changeLayoutSize();

    return false;
  }

  navigateHome() {
    this.menuService.navigateHome();
    return false;
  }

  loadCurrentTheme() {
    this.mThemeService.getUserAndSystemThemes()
    .subscribe((result: any) => {
      if (result.currentTheme) {
          this.currentTheme = result.currentTheme;
       }
    });
  }
}
