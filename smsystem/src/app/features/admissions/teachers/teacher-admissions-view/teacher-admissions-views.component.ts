import { Component } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { PageEvent } from '@angular/material';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import { Course } from 'app/models';
import { CourseService } from 'app/services';
import { QueryParam } from 'app/utils';
import { from } from 'rxjs';

@Component({
  selector: 'app-teacher-admissions-view',
  styleUrls: ['./teacher-admissions-views.component.scss'],
  templateUrl: './teacher-admissions-views.component.html',
})
export class TeacherAdmissionsViewComponent {

  param: QueryParam = {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: 'teacher',
  };

  title: string = 'List of Positions';
  hidePageSize: boolean = false;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'name', 'code', 'description', 'action'];
  dataSource: MatTableDataSource<Course> = new MatTableDataSource<Course>(TEACHER_ADMISSIONS.courses);


  edit(position: Position) {

  }
  delete(position: Position) {

  }
  onPageChange(pageEvent: PageEvent) {

  }
  open(){}

}


export interface TeacherCourseAdmission {
  id: number;
  teacherId: number;
  academicYearId: number;
  schoolClassId: number;
  capacity: number;
  teacherFullName: string;
  yearOfStudy: string;
  className: string;
  courses: Course[];
}

export const TEACHER_ADMISSIONS: TeacherCourseAdmission  = {
                            id: 1,
                            teacherId: 3,
                            academicYearId: 4,
                            schoolClassId: 8,
                            teacherFullName: 'Hallen Marley',
                            yearOfStudy: '2017/2018',
                            className: 'Grade 6',
                            capacity: 7,
                            courses: [
                              {
                                id: 23,
                                code: 'CN560',
                                name: 'Chemistry',
                                description: '',
                              },
                              {
                                id: 24,
                                code: 'CN561',
                                name: 'Mathematics',
                                description: '',
                              },
                              {
                                id: 25,
                                code: 'CN562',
                                name: 'Physics',
                                description: '',
                              },
                              {
                                id: 26,
                                code: 'CN563',
                                name: 'History',
                                description: '',
                              },
                              {
                                id: 27,
                                code: 'CN564',
                                name: 'Geography',
                                description: '',
                              },
                              {
                                id: 28,
                                code: 'CN567',
                                name: 'Civics',
                                description: '',
                              },
                            ],
                          };
