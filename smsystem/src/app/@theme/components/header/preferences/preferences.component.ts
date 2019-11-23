import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import {
  NbMediaBreakpointsService,
  NbMenuService,
  NbThemeService,
 } from '@nebular/theme';

import { map, takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

import { SystemConfig, UserPreference, SystemSetting } from 'app/models';
import { UserPreferenceService, AuthService} from 'app/services';
import { THEMES, APP_CONSTANTS } from 'app/utils';

@Component({
  selector: 'app-preferences',
  templateUrl: './preferences.component.html',
  styleUrls: ['./preferences.component.scss'],
})
export class PreferencesComponent implements OnInit {
  themes = THEMES;
  systemTheme: string = APP_CONSTANTS.currentTheme;
  private destroy$: Subject<void> = new Subject<void>();
  currentTheme = 'default';
  userPictureOnly: boolean = false;
  user: any;
  systemConfig: SystemConfig = null;
  userPreference: UserPreference = null;
  systemSetting: SystemSetting = null;

  constructor(
    private menuService: NbMenuService,
    private authService: AuthService,
    private themeService: NbThemeService,
    private userPreferenceService: UserPreferenceService,
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
      this.authService.isAuthenticated().subscribe(isAuthenticated => {
          if (isAuthenticated) {
            this.loadCurrentTheme();
          }
      });
  }

  changeTheme(themeName: string) {
    this.userPreference = {
      id:  this.systemSetting ? this.systemSetting.currentThemeID : null,
      userId: 0,
      name: this.systemTheme,
      value: themeName,
    };
    this.userPreferenceService.updateSystemConfig(this.userPreference)
    .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const content = resp.body;
      if (content.success) {
          this.userPreference = content.data;
          this.currentTheme =  this.userPreference.value;
          this.themeService.changeTheme(this.currentTheme);
      }
    }, error => {

    });
  }
  navigateHome() {
    this.menuService.navigateHome();
    return false;
  }

  loadCurrentTheme() {
    this.userPreferenceService.getSystemConfigByKey(this.systemTheme)
    .subscribe((result: any) => {
      if (result.success) {
        this.systemSetting = result.data;
        this.currentTheme =  this.systemSetting.currentTheme;
        this.themeService.changeTheme(this.currentTheme);
      }
    });
  }
}
