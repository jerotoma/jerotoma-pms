import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { NbDialogService } from '@nebular/theme';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

import { DialogComponent, SnackbarComponent } from 'app/shared/modals';
import { APP_CONSTANTS } from 'app/utils';

@Injectable()
export class ModalService {

    public isDialogOpen: Boolean = false;

    constructor(
      public dialog: MatDialog,
      private mDialogService: NbDialogService,
      private snackBar: MatSnackBar,
      private router: Router) { }

      openDialog(data: any): any {
        if (this.isDialogOpen) {
            return false;
        }
        this.isDialogOpen = true;
        const dialogRef = this.dialog.open(DialogComponent, {
            width: '300px',
            data: data,
        });

        dialogRef.afterClosed().subscribe(result => {
            this.logSuccessResponse('The dialog was closed');
            this.isDialogOpen = false;
            let animal;
            animal = result;
        });
    }

    openSnackBar(message: string, panelClass: string) {
      this.snackBar.openFromComponent(SnackbarComponent, {
        data: {
          message: message,
        },
        horizontalPosition: 'right',
        verticalPosition: 'top',
        panelClass: panelClass,
        duration: APP_CONSTANTS.popupTimeout,
      });
    }

    popup(popupSubPath: string, error?: string, statusText?: string) {
      this.logSuccessResponse(error);
      this.router.navigate([this.router.url + '?error=' + error]);
    }

   logErrorResponse(errorMessage: any) {
      window.console.error(this, errorMessage);
    }

   logSuccessResponse(message: any) {
      window.console.log(this, message);
    }
}
