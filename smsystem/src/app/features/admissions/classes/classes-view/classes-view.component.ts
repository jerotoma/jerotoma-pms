import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { PageEvent } from '@angular/material';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { ClassCreateComponent } from '../class-create/class-create.component';

import { JClassView, JClassAdmission } from 'app/models';
import { ClassService } from 'app/services';
import { QueryParam } from 'app/utils';




@Component({
  selector: 'app-classes-view',
  styleUrls: ['./classes-view.component.scss'],
  templateUrl: './classes-view.component.html',
})
export class ClassesViewComponent implements OnInit {
    @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
    @ViewChild(MatSort, {static: true}) sort: MatSort;

    param: QueryParam = {
      page: 1,
      pageSize: 10,
      orderby: 'DESC',
      status: '',
      search: '',
      fieldName: '',
      userType: 'teacher',
    };

    title: string = 'List of Scheduled Courses';
    hidePageSize: boolean = false;
    totalNumberOfItems: number = 20;
    pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
    displayedColumns: string[] = ['id', 'course', 'courseCode', 'academicYear', 'academicYearTerm', 'capacity', 'teacher', 'action'];
    dataSource: MatTableDataSource<JClassView > = new MatTableDataSource<JClassView >();

    constructor(
      private classService: ClassService,
      private dialogService: NbDialogService,
      ) {
    }
    ngOnInit() {
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.loadClasses();
    }
    loadClasses() {
      this.classService.getClasses(this.param)
        .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
          const resp = result;
          const status = resp.status;
          if (status !== null && status === 200 && resp.body) {
            const data = resp.body.data;
            this.totalNumberOfItems = data.count;
            this.dataSource = new MatTableDataSource<JClassView>(data.jClasses);
          }
        }, error => {

      });
    }

    open() {
      this.dialogService.open(ClassCreateComponent, {
        context: {
          title: 'Add New Class',
          action: 'create',
        },
      }).onClose.subscribe(_data => {
        this.loadClasses();
      });
    }

    edit(jClassAdmission: JClassAdmission) {

    }
    delete(jClassAdmission: JClassAdmission) {

    }
    onPageChange(pageEvent: PageEvent) {

    }

}
