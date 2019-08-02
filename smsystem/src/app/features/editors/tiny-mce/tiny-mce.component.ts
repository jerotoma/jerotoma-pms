import { Component } from '@angular/core';

@Component({
  selector: 'app-tiny-mce-page',
  template: `
    <nb-card>
      <nb-card-header>
        Tiny MCE
      </nb-card-header>
      <nb-card-body>
        <app-tiny-mce></app-tiny-mce>
      </nb-card-body>
    </nb-card>
  `,
})
export class TinyMCEComponent {
}
