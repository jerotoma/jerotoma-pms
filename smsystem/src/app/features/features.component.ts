import { Component, OnInit } from '@angular/core';
import { NbMenuItem } from '@nebular/theme';
import { MENU_ITEMS } from './dashboard-menu';
import { AuthService } from 'app/services/auth';
import { Role, ADMINS_AND_EXECUTIVES_ROLES, USER_ROLE } from 'app/models';
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
  currentUserRoles: USER_ROLE[] = [];

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.filterMenuItemsByRoles();
  }

  filterMenuItemsByRoles() {
    const mItems = this.menuItems;
    this.currentUserRoles = this.authService.loadCurrentUserRoles();
    this.menuItems = [];
    for (const [itemKey, itemValue] of Object.entries(mItems)) {
      for (const [key, value] of Object.entries(FRONTEND_ENDPOINT_VALUES)) {
        if (itemValue.link === value.path) {
          const menuItem = this.processAllowedRole(itemValue, value.allowedRoles);
          if (menuItem.children) {
            const childMenuItems = [];
            for (const [childItemKey, childItemValue] of Object.entries(menuItem.children)) {
              for (const [childKey, childValue] of Object.entries(FRONTEND_ENDPOINT_VALUES)) {
                if (childValue.path === childItemValue.link) {
                    childMenuItems.push(this.processAllowedRole(childItemValue, childValue.allowedRoles));
                }
              }
            }
            menuItem.children = childMenuItems;
          }
          this.menuItems.push(menuItem);
          break;
        } else if (itemValue.group) {
          if (itemValue.title === 'SETTINGS') {
            for (let j = 0; j < this.currentUserRoles.length; j++) {
              if (ADMINS_AND_EXECUTIVES_ROLES.indexOf(this.currentUserRoles[j]) !== -1) {
                itemValue.hidden = false;
                break;
              } else {
                itemValue.hidden = true;
                break;
              }
            }
          } else {
            itemValue.hidden = false;
          }
          this.menuItems.push(itemValue);
          break;
        }
      }
    }
  }


  processAllowedRole(menuItem: NbMenuItem, userRoles: USER_ROLE[]) {
    const roles: USER_ROLE[] = this.authService.loadCurrentUserRoles();
    const childMenuItems: NbMenuItem[] = [];
    for (let j = 0; j < roles.length; j++) {
      if (userRoles.indexOf(roles[j]) !== -1) {
        menuItem.hidden = false;
        break;
      } else {
        menuItem.hidden = true;
        break;
      }
    }
    return menuItem;
  }

}
