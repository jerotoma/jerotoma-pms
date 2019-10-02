import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { PageEvent } from '@angular/material';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { ClassCreateComponent } from '../class-create/class-create.component';
import { DeleteModalComponent } from 'app/shared';

import { JClassView} from 'app/models';
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
    isLoading: boolean = false;
    totalNumberOfItems: number = 20;
    pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
    displayedColumns: string[] = ['id', 'course', 'courseCode', 'capacity', 'academicYearTerm', 'academicYear', 'teacher', 'action'];
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
      this.isLoading = true;
      this.classService.getClasses(this.param)
        .subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
          const resp = result;
          const status = resp.status;
          this.isLoading = false;
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
      }).onClose.subscribe(result => {
        if (result.isClassCreated) {
          this.loadClasses();
        }
      });
    }

    edit(jClassView: JClassView) {
     this.dialogService.open(ClassCreateComponent, {
        context: {
          title: 'Edit Class',
          action: 'edit',
          id: jClassView.id.toString(),
        },
      }).onClose.subscribe(result => {
        if (result.isClassCreated) {
          this.loadClasses();
        }
      });
    }
    delete(jClassView: JClassView) {
      this.dialogService.open(DeleteModalComponent, {
        context: {
          title: 'Delete Class',
          action: 'delete',
          id: jClassView.id.toString(),
        },
      }).onClose.subscribe(result => {
        if (result.confirmed) {
          this.deleteClass(result.id);
        }
      });
    }
    onPageChange(pageEvent: PageEvent) {
      this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
      this.param.pageSize = pageEvent.pageSize;
      this.loadClasses();
    }

    deleteClass(classId: number) {
      this.classService.deleteClass(classId).subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
        const resp = result;
        const status = resp.status;
        if (status !== null && status === 200 ) {
          this.loadClasses();
        }
      });
    }

}
