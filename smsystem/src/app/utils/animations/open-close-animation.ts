import {
  trigger,
  state,
  style,
  animate,
  transition,
  // ...
} from '@angular/animations';

export const OPEN_CLOSE_ANIMATION = [
  trigger('openClose', [
    state('open', style({
      opacity: 1,
      display: 'block',
    })),
    state('closed', style({
      opacity: 0,
      display: 'none',
    })),
    transition('open => closed', [
      animate('1.5s'),
    ]),
    transition('closed => open', [
      animate('0.2s'),
    ]),
  ]),
];
