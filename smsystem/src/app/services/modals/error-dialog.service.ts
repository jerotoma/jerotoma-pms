import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { NbDialogService } from '@nebular/theme';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';

import { SnackbarComponent } from 'app/shared/snackbars';
import { ErrorDialogComponent } from 'app/shared/error-dialog';

@Injectable()
export class ErrorDialogService {

    public isDialogOpen: Boolean = false;

    constructor(
      public dialog: MatDialog,
      private modalService: NbDialogService,
      private snackBar: MatSnackBar,
      private router: Router) { }

      openDialog(data): any {
        if (this.isDialogOpen) {
            return false;
        }
        this.isDialogOpen = true;
        const dialogRef = this.dialog.open(ErrorDialogComponent, {
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
        horizontalPosition: 'center',
        panelClass: panelClass,
        duration: 50000,
      });
    }

    popup(popupSubPath: string, error?: string, statusText?: string) {
      this.logSuccessResponse(error);
      this.router.navigate([this.router.url + '?error=' + error]);
    }

    private logErrorResponse(errorMessage: any) {
      window.console.error(this, errorMessage);
    }

    private logSuccessResponse(message: any) {
      window.console.log(this, message);
    }
}
