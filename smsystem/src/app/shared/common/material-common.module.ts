import { NgModule } from '@angular/core';
import {
  MatButtonModule,
  MatTableModule,
  MatPaginatorModule,
  MatIconModule,
  MatSelectModule,
  MatMenuModule,
  MatDialog,
  MatSnackBarModule,
  MAT_SNACK_BAR_DEFAULT_OPTIONS,
} from '@angular/material';



const MODULES = [
  MatTableModule,
  MatPaginatorModule,
  MatIconModule,
  MatMenuModule,
  MatSelectModule,
  MatSnackBarModule,
  MatButtonModule,
];

const EXPORT_MODULES = [
  MatTableModule,
  MatPaginatorModule,
  MatIconModule,
  MatMenuModule,
  MatSelectModule,
  MatSnackBarModule,
  MatButtonModule,
];

const SERVICES = [
  MatDialog,
];


@NgModule({
  imports: [
    ...MODULES,
  ],
  exports: [
    ...EXPORT_MODULES,
  ],
  providers: [
   ...SERVICES,
   {
     provide: MAT_SNACK_BAR_DEFAULT_OPTIONS,
     useValue: {
       duration: 6500,
      },
    },
  ],
})
export class MaterialCommonModule {}
