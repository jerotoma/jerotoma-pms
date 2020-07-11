import { Component, OnInit } from '@angular/core';
import { NbMenuItem } from '@nebular/theme';
import { MENU_ITEMS } from './dashboard-menu';
import { AuthService } from 'app/services/auth';
import { Role, ADMINS_AND_EXECUTIVES_ROLES } from 'app/models';
import { FRONTEND_ENDPOINT_VALUES } from 'app/utils';

@Component({
  selector: 'app-features',
  styleUrls: ['features.component.scss'],
  template: `
    <app-one-column-layout>
      <nb-menu
        [autoCollapse]="autoCollapse"
        [items]="menuItems">
      </nb-menu>
      <router-outlet></router-outlet>
    </app-one-column-layout>
  `,
})
export class FeaturesComponent implements OnInit {
  menuItems = MENU_ITEMS;
  autoCollapse: boolean = true;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
  //  this.filterMenuItemsByRoles();
  }

  filterMenuItemsByRoles() {
    const roles: Role[] = this.authService.loadCurrentUserRoles();
    this.menuItems = this.menuItems.filter((menuItem: NbMenuItem) => {
      for (const [key, value] of Object.entries(FRONTEND_ENDPOINT_VALUES)) {
        if (value.path === menuItem.link) {
          for (let j = 0; j < roles.length; j++) {
            if (value.allowedRoles.indexOf(roles[j].name) !== -1) {
              return true;
            }
          }
        } else if (menuItem.group) {
          if (menuItem.title === 'SETTINGS') {
            for (let j = 0; j < roles.length; j++) {
              if (ADMINS_AND_EXECUTIVES_ROLES.indexOf(roles[j].name) !== -1) {
                return true;
              }
            }
            return false;
          }
          return true;
        }
      }
      return false;
    });
  }
}
