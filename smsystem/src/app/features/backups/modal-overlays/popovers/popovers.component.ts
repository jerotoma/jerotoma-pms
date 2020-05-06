import { Component } from '@angular/core';
import {
  appPopoverCardComponent, appPopoverFormComponent,
  appPopoverTabsComponent,
} from './popover-examples.component';

@Component({
  selector: 'app-popovers',
  styleUrls: ['./popovers.component.scss'],
  templateUrl: './popovers.component.html',
})
export class PopoversComponent {
  tabsComponent = appPopoverTabsComponent;
  cardComponent = appPopoverCardComponent;
  formComponent = appPopoverFormComponent;
}
