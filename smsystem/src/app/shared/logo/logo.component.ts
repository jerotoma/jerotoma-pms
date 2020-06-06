import { Component, OnInit, Output, Input, EventEmitter } from '@angular/core';

import {APP_CONSTANTS } from 'app/utils';


@Component({
  selector: 'app-logo',
  templateUrl: 'logo.component.html',
  styleUrls: ['logo.component.scss'],
})
export class LogoComponent implements OnInit {

  appLogo: string = APP_CONSTANTS.appLogo;
  appName: string = APP_CONSTANTS.appName;

  constructor() {}

  ngOnInit(): void {

  }

  getAppLogo() {
    return '';
  }
}
