import { Component, OnInit, Inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSnackBarRef, MAT_SNACK_BAR_DATA } from '@angular/material';

@Component({
  selector: 'app-snackbar',
  templateUrl: './snackbar.component.html',
  styleUrls: ['./snackbar.component.scss'],
})
export class SnackbarComponent implements OnInit {

  durationInSeconds: number = 5;

  constructor(
    private snackBar: MatSnackBar,
    public snackBarRef: MatSnackBarRef<SnackbarComponent>,
    @Inject(MAT_SNACK_BAR_DATA) public data: any) {

    }
  ngOnInit() {
  }

  onClose() {
    this.snackBarRef.dismiss();
  }
}
