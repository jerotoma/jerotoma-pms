import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { NbDialogService } from '@nebular/theme';

@Injectable()
export class DialogService {
  private safeToClose = false;

  constructor(
    private router: Router,
    private modalService: NbDialogService,
  ) { }

  popup(popupSubPath: string, error?: string, statusText?: string) {
      this.router.navigate(['popup/' + popupSubPath ]);
  }
}
