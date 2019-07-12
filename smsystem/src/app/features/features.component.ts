import { Component } from '@angular/core';

import { MENU_ITEMS } from './dashboard-menu';

@Component({
  selector: 'ngx-pages',
  styleUrls: ['features.component.scss'],
  template: `
    <ngx-one-column-layout>
      <nb-menu [items]="menu"></nb-menu>
      <router-outlet></router-outlet>
    </ngx-one-column-layout>
  `,
})
export class FeaturesComponent {

  menu = MENU_ITEMS;
}
