import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import {
  NbMediaBreakpointsService,
  NbMenuService,
  NbSidebarService,
  NbThemeService,
 } from '@nebular/theme';

import { map, takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

import { THEMES } from '../header-menu';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss'],
})
export class SettingsComponent implements OnInit {
  themes = THEMES;
  private destroy$: Subject<void> = new Subject<void>();
  currentTheme = 'default';
  userPictureOnly: boolean = false;
  user: any;

  constructor(
    private sidebarService: NbSidebarService,
    private menuService: NbMenuService,
    private themeService: NbThemeService,
    private route: ActivatedRoute,
    private router: Router,
    private breakpointService: NbMediaBreakpointsService) {

    }

  ngOnInit() {

    this.currentTheme = this.themeService.currentTheme;
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
  }

  changeTheme(themeName: string) {
    this.themeService.changeTheme(themeName);
  }
  navigateHome() {
    this.menuService.navigateHome();
    return false;
  }

}
