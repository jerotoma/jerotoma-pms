import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import {
  NbMediaBreakpointsService,
  NbMenuService,
  NbThemeService,
 } from '@nebular/theme';

import { map, takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

import { SystemConfig, UserPreference, SystemSetting, Theme } from 'app/models';
import { UserPreferenceService, AuthService, ThemeService} from 'app/services';
import { THEMES, APP_CONSTANTS } from 'app/utils';

@Component({
  selector: 'app-preferences',
  templateUrl: './preferences.component.html',
  styleUrls: ['./preferences.component.scss'],
})
export class PreferencesComponent implements OnInit {
  themes = THEMES;
  userPreferenceTheme: string = APP_CONSTANTS.userPreferenceTheme;
  private destroy$: Subject<void> = new Subject<void>();
  currentTheme = 'default';
  mTheme: Theme = null;
  userPictureOnly: boolean = false;
  user: any;
  systemConfig: SystemConfig = null;
  overrideSystemConfig: SystemConfig = null;
  userPreference: UserPreference = null;

  constructor(
    private menuService: NbMenuService,
    private authService: AuthService,
    private mThemeService: ThemeService,
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
      this.loadCurrentTheme();

   /* this.themeService.onThemeChange()
      .pipe(
        map(({ name }) => name),
        takeUntil(this.destroy$),
      )
      .subscribe(themeName => this.currentTheme = themeName); */

  }

  changeTheme(themeName: string) {
    this.userPreference = {
      id:  this.userPreference ? this.userPreference.id : null,
      userId: 0,
      name:  this.userPreference ? this.userPreference.name : this.userPreferenceTheme,
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
    this.mThemeService.getUserAndSystemThemes()
    .subscribe((result: any) => {
      if (result.success) {
        this.mTheme = result.data;
        this.systemConfig = this.mTheme.mapSystemConfigs ? this.mTheme.mapSystemConfigs.currentTheme : null;
        this.userPreference = this.mTheme.mapUserPreferences ? this.mTheme.mapUserPreferences.currentUserTheme : null;
        this.overrideSystemConfig = this.mTheme.mapSystemConfigs ? this.mTheme.mapSystemConfigs.overrideUserTheme : null;
        this.currentTheme =  this.userPreference ? this.userPreference.value : this.systemConfig.value;
        if (this.overrideSystemConfig && this.overrideSystemConfig.value) {
            this.themeService.changeTheme(this.systemConfig.value);
        } else {
          this.themeService.changeTheme(this.userPreference.value);
        }
      }
    });
  }
}
