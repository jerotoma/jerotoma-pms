import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap, Data } from '@angular/router';
import { NbAuthService } from '@nebular/auth';
import { NbMediaBreakpointsService, NbMenuService, NbSidebarService, NbThemeService } from '@nebular/theme';

import { UserService } from 'app/services/users/user.service';
import { User } from 'app/models/users/user.model';


import { LayoutService } from 'app/@core/utils';
import { map, takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

import { USER_DROPDOWN_ITEMS, THEMES } from './header-menu';

@Component({
  selector: 'app-header',
  styleUrls: ['./header.component.scss'],
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit, OnDestroy {

  private destroy$: Subject<void> = new Subject<void>();
  userPictureOnly: boolean = false;
  user: User;
  themes = THEMES;
  currentTheme = 'default';
  userMenu = USER_DROPDOWN_ITEMS;

  constructor(
              private userService: UserService,
              private authService: NbAuthService,
              private sidebarService: NbSidebarService,
              private menuService: NbMenuService,
              private themeService: NbThemeService,
              private route: ActivatedRoute,
              private router: Router,
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

    this.themeService.onThemeChange()
      .pipe(
        map(({ name }) => name),
        takeUntil(this.destroy$),
      )
      .subscribe(themeName => this.currentTheme = themeName);

      this.menuService.onItemClick().subscribe(( event ) => {
        this.onItemSelection(event.item.title);
      });
  }

  loadCurrentUser() {
    if (this.authService.isAuthenticatedOrRefresh()) {
      this.userService.getCurrentUser().subscribe((result: any) => {
        this.user = result.data;
      });
    }
  }
  onItemSelection( title ) {
    if ( title === 'Log out' ) {
      // Do something on Log out
      console.log('Log out Clicked ');
    } else if ( title === 'Profile' ) {
      // Do something on Profile
      console.log('Profile Clicked ');
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
}
