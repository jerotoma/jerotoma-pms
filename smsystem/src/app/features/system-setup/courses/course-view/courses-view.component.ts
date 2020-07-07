import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { PageEvent } from '@angular/material/paginator';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { CourseCreateComponent } from '../course-create/course-create.component';
import { CourseDeleteComponent } from '../course-delete/course-delete.component';
import { Course, ResponseWrapper } from 'app/models';
import { CourseService } from 'app/services';
import { QueryParam } from 'app/utils';
/**
 * @title Table with pagination
 */
@Component({
  selector: 'app-courses-view',
  styleUrls: ['courses-view.component.scss'],
  templateUrl: 'courses-view.component.html',
})
export class CoursesViewComponent implements OnInit {

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  title: string = 'List of Courses';
  course: Course;
  hidePageSize: boolean = false;
  isLoading: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'name', 'code', 'academicLevel', 'description', 'action'];
  dataSource: MatTableDataSource<Course> = new MatTableDataSource<Course>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(
    private courseService: CourseService,
    private dialogService: NbDialogService,
    ) {
  }
  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.loadCourses();
  }

  open() {
    this.dialogService.open(CourseCreateComponent, {
      context: {
        title: 'Add New Course',
        action: 'create',
      },
    }).onClose.subscribe(_data => {
      this.loadCourses();
    });
  }

  loadCourses() {
    this.isLoading = true;
    this.courseService.getCourses(this.param)
      .subscribe((resp: ResponseWrapper) => {
        this.isLoading = false;
        if (resp) {
          const data = resp.data;
          this.totalNumberOfItems = data.count;
          this.dataSource = new MatTableDataSource<Course>(data.courses);
        }
      });
  }
  edit(course: Course) {
    this.dialogService.open(CourseCreateComponent, {
      context: {
        title: 'Edit Course',
        action: 'edit',
        id: course.id.toString(),
      },
    }).onClose.subscribe(_data => {
      this.loadCourses();
    });
  }
  delete(course: Course) {
    this.dialogService.open(CourseDeleteComponent, {
      context: {
        title: 'Delete Course',
        action: 'delete',
        courseId: course.id.toString(),
        name: course.name,
      },
    }).onClose.subscribe(_data => {
      this.loadCourses();
    });
  }
  onPageChange(pageEvent: PageEvent) {
    this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
    this.param.pageSize = pageEvent.pageSize;
    this.loadCourses();
  }
}
