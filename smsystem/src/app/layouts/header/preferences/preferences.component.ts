import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import {
  NbMediaBreakpointsService,
  NbMenuService,
  NbThemeService,
 } from '@nebular/theme';

import { map, takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

import { SystemConfig, UserPreference } from 'app/models';
import { UserPreferenceService, ThemeService} from 'app/services';
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
  systemTheme = 'default';
  userPictureOnly: boolean = false;
  overrideUserTheme: boolean = false;
  userTheme: string = 'default';
  user: any;
  overrideSystemConfig: SystemConfig = null;
  userPreference: UserPreference = null;

  constructor(
    private menuService: NbMenuService,
    private mThemeService: ThemeService,
    private themeService: NbThemeService,
    private userPreferenceService: UserPreferenceService,
    private breakpointService: NbMediaBreakpointsService) {

    }

  ngOnInit() {
    this.systemTheme = this.themeService.currentTheme;
    this.loadCurrentTheme();
    const { xl } = this.breakpointService.getBreakpointsMap();
    this.themeService.onMediaQueryChange()
      .pipe(
        map(([, currentBreakpoint]) => currentBreakpoint.width < xl),
        takeUntil(this.destroy$),
      )
      .subscribe((isLessThanXl: boolean) => this.userPictureOnly = isLessThanXl);
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
          this.loadCurrentTheme();
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
      if (result) {
       this.systemTheme = result.systemTheme;
       this.overrideUserTheme = result.overrideUserTheme;
       this.userTheme = result.userTheme;
      }
    });
  }
}
