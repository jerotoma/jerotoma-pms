import { Component, OnDestroy, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { NbThemeService } from '@nebular/theme';

import {
  NbAuthComponent,
  NbAuthService,
} from '@nebular/auth';

import { APP_CONSTANTS } from 'app/utils';
import {SystemConfig, SystemSetting, Theme } from 'app/models';
import {ThemeService } from 'app/services';

@Component({
  selector: 'app-auth',
  styleUrls: ['./auth.component.scss'],
  templateUrl: './auth.component.html',
})
export class AuthComponent extends NbAuthComponent implements OnDestroy, OnInit {
    // showcase of how to use the onAuthenticationChange method

  currentTheme = 'default';
  systemTheme: string = APP_CONSTANTS.currentTheme;
  systemConfig: SystemConfig = null;
  systemSetting: SystemSetting = null;
  mTheme: Theme = null;

  constructor(
      protected auth: NbAuthService,
      private themeService: NbThemeService,
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
      if (result.success) {
        this.mTheme = result.data;
        if (this.mTheme.overrideUserTheme) {
          this.currentTheme =  this.mTheme.systemTheme ? this.mTheme.systemTheme : this.mTheme.userTheme;
        } else {
          this.currentTheme =  this.mTheme.userTheme ? this.mTheme.userTheme : this.mTheme.systemTheme;
        }
        this.themeService.changeTheme(this.currentTheme);
       }
    });
  }
}
