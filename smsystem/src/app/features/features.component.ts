import { Component, OnInit } from '@angular/core';

import { MENU_ITEMS } from './dashboard-menu';

@Component({
  selector: 'app-features',
  styleUrls: ['features.component.scss'],
  template: `
    <app-one-column-layout>
      <nb-menu
        [autoCollapse]="autoCollapse"
        [items]="menu">
      </nb-menu>
      <router-outlet></router-outlet>
    </app-one-column-layout>
  `,
})
export class FeaturesComponent implements OnInit {
  menu = MENU_ITEMS;
  autoCollapse: boolean = true;

  ngOnInit(): void {

  }
}
