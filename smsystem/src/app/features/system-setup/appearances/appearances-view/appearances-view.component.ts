import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import {
  NbMediaBreakpointsService,
  NbMenuService,
  NbThemeService,
 } from '@nebular/theme';

import { map, takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

import {SystemConfig, Theme, UserPreference } from 'app/models';
import {SystemConfigService, ThemeService } from 'app/services';
import { THEMES, APP_CONSTANTS } from 'app/utils';

@Component({
  selector: 'app-appearances-view',
  templateUrl: './appearances-view.component.html',
  styleUrls: ['./appearances-view.component.scss'],
})
export class AppearancesViewComponent implements OnInit {
  themes = THEMES;
  systemTheme: string = APP_CONSTANTS.currentTheme;
  private destroy$: Subject<void> = new Subject<void>();
  currentTheme = 'default';
  mTheme: Theme = null;
  userPictureOnly: boolean = false;
  user: any;
  overrideUserTheme: boolean = false;
  systemConfig: SystemConfig = null;
  overrideSystemConfig: SystemConfig = null;
  userPreference: UserPreference = null;

  constructor(
    private menuService: NbMenuService,
    private themeService: NbThemeService,
    private mThemeService: ThemeService,
    private systemConfigService: SystemConfigService,
    private breakpointService: NbMediaBreakpointsService) {

    }

  ngOnInit() {
    this.themeInit();
  }
  overrideUserThemeCheckedChange(isOverrideUserTheme: any) {
    const systemConfig = {
      id: this.overrideSystemConfig ? this.overrideSystemConfig.id : null,
      name: APP_CONSTANTS.overrideUserTheme,
      value: isOverrideUserTheme,
    };
    this.updateSystemConfigChange(systemConfig);
  }
  changeTheme(themeName: string) {
   const systemConfig = {
      id: this.systemConfig ? this.systemConfig.id : null,
      name: this.systemConfig ? this.systemConfig.name : APP_CONSTANTS.currentTheme,
      value: themeName,
    };
    this.updateSystemConfigChange(systemConfig);
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

  themeInit() {
    this.currentTheme = this.themeService.currentTheme;
    const { xl } = this.breakpointService.getBreakpointsMap();
    this.themeService.onMediaQueryChange()
      .pipe(
        map(([, currentBreakpoint]) => currentBreakpoint.width < xl),
        takeUntil(this.destroy$),
      )
      .subscribe((isLessThanXl: boolean) => this.userPictureOnly = isLessThanXl);

      this.loadCurrentTheme();

   /*  this.themeService.onThemeChange()
      .pipe(
        map(({ name }) => name),
        takeUntil(this.destroy$),
      )
      .subscribe(themeName => this.currentTheme = themeName); */
  }

  updateSystemConfigChange(systemConfig: SystemConfig) {
    this.systemConfigService.updateSystemConfig(systemConfig)
    .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const content = resp.body;
      if (content.success) {
         this.loadCurrentTheme();
      }
    }, error => {

    });
  }
}
