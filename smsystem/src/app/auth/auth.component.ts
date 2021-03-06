import { Component, OnDestroy, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { NbThemeService } from '@nebular/theme';

import {
  NbAuthComponent,
  NbAuthService,
} from '@nebular/auth';

import { APP_CONSTANTS } from 'app/utils';
import {SystemConfig, SystemSetting, Theme , UserPreference} from 'app/models';
import {ThemeService } from 'app/services';
import { authOptions } from './auth.options';


@Component({
  selector: 'app-auth',
  styleUrls: ['./auth.component.scss'],
  templateUrl: './auth.component.html',
})
export class AuthComponent extends NbAuthComponent implements OnDestroy, OnInit {
    // showcase of how to use the onAuthenticationChange method

  currentTheme = 'default';
  systemTheme: string = APP_CONSTANTS.currentTheme;
  appName: string = APP_CONSTANTS.appName;
  systemConfig: SystemConfig = null;
  systemSetting: SystemSetting = null;
  mTheme: Theme = null;
  overrideSystemConfig: SystemConfig = null;
  userPreference: UserPreference = null;
  showRegister: boolean = authOptions.showRegister;

  constructor(
      protected auth: NbAuthService,
      private mThemeService: ThemeService,
      protected location: Location) {
        super(auth, location);
  }

  ngOnInit() {
    this.loadCurrentTheme();
  }

  loadCurrentTheme() {
    this.mThemeService.getCurrentSystemTheme()
    .subscribe((result: any) => {
      if (result.currentTheme) {
          this.currentTheme = result.currentTheme;
       }
    });
  }
}
