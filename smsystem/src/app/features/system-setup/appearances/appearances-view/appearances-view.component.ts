import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import {
  NbMediaBreakpointsService,
  NbMenuService,
  NbThemeService,
 } from '@nebular/theme';

import { map, takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

import {SystemConfig, Theme } from 'app/models';
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
      id: this.mTheme.overrideUserThemeID,
      name: APP_CONSTANTS.overrideUserTheme,
      value: isOverrideUserTheme,
    };
    this.updateSystemConfigChange(systemConfig);
  }
  changeTheme(themeName: string) {
    const systemConfig = {
      id: this.mTheme.systemThemeID,
      name: this.systemTheme,
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
      if (result.success) {
        this.mTheme = result.data;
        this.currentTheme =  this.mTheme.systemTheme;
        if (this.mTheme.overrideUserTheme) {
          this.themeService.changeTheme(this.currentTheme);
        } else {
          this.themeService.changeTheme(this.mTheme.userTheme);
        }
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
