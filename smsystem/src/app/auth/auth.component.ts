import { Component, OnDestroy, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { NbThemeService } from '@nebular/theme';

import {
  NbAuthComponent,
  NbAuthService,
} from '@nebular/auth';

import { APP_CONSTANTS } from 'app/utils';
import {SystemConfig, SystemSetting } from 'app/models';
import {SystemConfigService } from 'app/services';

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

  constructor(
      protected auth: NbAuthService,
      private themeService: NbThemeService,
      private systemConfigService: SystemConfigService,
      protected location: Location) {
        super(auth, location);
  }

  ngOnInit() {
    this.loadCurrentTheme();
  }

  loadCurrentTheme() {
    this.systemConfigService.getSystemConfigByKey(this.systemTheme)
    .subscribe((result: any) => {
      if (result.success) {
        this.systemSetting = result.data;
        this.currentTheme =  this.systemSetting.currentTheme;
        this.themeService.changeTheme(this.currentTheme);
      }
    });
  }
}
