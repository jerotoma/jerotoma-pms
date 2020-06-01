import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-counter-card',
  styleUrls: ['./counter-card.component.scss'],
  template: `
    <nb-card>
      <div class="icon-container">
        <div class="icon status-{{ type }}">
          <ng-content></ng-content>
        </div>
      </div>
      <div class="details">
        <div class="title h5">{{ count }}</div>
        <div class="counter">{{ title }}</div>
      </div>
    </nb-card>
  `,
})
export class CounterCardComponent {

  @Input() title: string;
  @Input() type: string;
  @Input() count: number;
}
