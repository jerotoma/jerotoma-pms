import { Component, ViewChild } from '@angular/core';

@Component({
  selector: 'ngx-accordion',
  templateUrl: 'other-staffs.component.html',
  styleUrls: ['other-staffs.component.scss'],
})
export class OtherStaffsComponent {

  @ViewChild('item', { static: true }) accordion;

  toggle() {
    this.accordion.toggle();
  }
}
