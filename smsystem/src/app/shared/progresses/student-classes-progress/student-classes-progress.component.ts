import { Component, OnInit, Input } from '@angular/core';
import {
  StudentProgressService,
  StatusService,
 } from 'app/services';
import { USER_TYPE, QueryParam } from 'app/utils';
import {
  Student,
  StudentClassesProgress,
} from 'app/models';

@Component({
  selector: 'app-student-classes-progress',
  templateUrl: './student-classes-progress.component.html',
  styleUrls: ['./student-classes-progress.component.scss'],
})
export class StudentClassesProgressComponent implements OnInit {

  @Input('student') student: Student;
  @Input('title') title: string = 'Classes Progress';

  studentClassesProgress: StudentClassesProgress;

  param: QueryParam =  {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
  };

  constructor(
    private studentProgressService: StudentProgressService,
    private statusService: StatusService,
   ) { }

  ngOnInit() {
    this.loadStudentAcademicLevelsProgressByStudentId();
  }

  loadStudentAcademicLevelsProgressByStudentId() {
    this.studentProgressService.findStudentClassesProgressByStudentId(this.student.id)
    .subscribe((studentClassesProgress: StudentClassesProgress) => {
      this.studentClassesProgress = studentClassesProgress;
    });
  }
  preventDefaultJClass(event: any) {
    event.preventDefault();
  }

  getStatusClass(statusName: string): string {
    return this.statusService.getCompletionStatusClass(statusName);
  }
}
