import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import {
  NbMediaBreakpointsService,
  NbMenuService,
  NbThemeService,
 } from '@nebular/theme';

import { map, takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

import {SystemConfig } from 'app/models';
import {SystemConfigService } from 'app/services';
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
  userPictureOnly: boolean = false;
  user: any;
  systemConfig: SystemConfig = null;

  constructor(
    private menuService: NbMenuService,
    private themeService: NbThemeService,
    private systemConfigService: SystemConfigService,
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
    this.loadCurrentTheme();
  }

  changeTheme(themeName: string) {
    this.systemConfig = {
      id: this.systemConfig.id,
      name: this.systemTheme,
      value: themeName,
    };
    this.systemConfigService.updateSystemConfig(this.systemConfig)
    .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const content = resp.body;
      if (content.success) {
          this.systemConfig = content.data;
          this.currentTheme =  this.systemConfig.value;
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
    this.systemConfigService.getSystemConfigByKey(this.systemTheme)
    .subscribe((result: any) => {
      if (result.success) {
        this.systemConfig = result.data;
        this.currentTheme =  this.systemConfig.value;
        this.themeService.changeTheme(this.currentTheme);
      }
    });
  }
}
