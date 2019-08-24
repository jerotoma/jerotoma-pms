import { Component, ViewChild } from '@angular/core';

@Component({
  selector: 'app-staffs',
  templateUrl: 'staffs.component.html',
  styleUrls: ['staffs.component.scss'],
})
export class StaffsComponent {

  @ViewChild('item', { static: true }) accordion;

  toggle() {
    this.accordion.toggle();
  }
}
