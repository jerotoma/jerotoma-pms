import { Component, OnInit, Input } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import {NbDialogService } from '@nebular/theme';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { AcademicLevelStudentEnrollmentCreateComponent } from 'app/shared/enrollments/academic-levels';
import { ChangeAcademicLevelProgressStatusComponent } from 'app/shared/progresses/progress-statuses';
import {
  StudentProgressService,
  StatusService,
 } from 'app/services';
import {
  Student,
  AcademicLevelProgress,
  StudentAcademicLevelsProgress,
} from 'app/models';

@Component({
  selector: 'app-student-academic-levels-progress',
  templateUrl: './student-academic-levels-progress.component.html',
  styleUrls: ['./student-academic-levels-progress.component.scss'],
})
export class StudentAcademicLevelsProgressComponent implements OnInit {

    @Input('student') student: Student;
    @Input('title') title: string = 'Student Academic Levels'

    studentProgress: StudentAcademicLevelsProgress;
    academicLevelProgresses: AcademicLevelProgress[];
    studentAcademicLevelsCompletionAvarage: number = 0;
    hidePageSize: boolean = false;
    isLoading: boolean = false;
    totalNumberOfItems: number = 20;
    pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
    displayedColumns: string[] = ['id', 'academicLevel', 'academicYear', 'completionStatus', 'action'];
    dataSource: MatTableDataSource<AcademicLevelProgress> = new MatTableDataSource<AcademicLevelProgress>();

  constructor(
    private dialogService: NbDialogService,
    private statusService: StatusService,
    private studentProgressService: StudentProgressService) { }

  ngOnInit(): void {
    this.loadStudentAcademicLevelsProgressByStudentId();
  }

  loadStudentAcademicLevelsProgressByStudentId() {
    this.studentProgressService
    .findStudentAcademicLevelsProgressByStudentId(this.student.id)
    .subscribe((studentAcademicLevelsProgress: StudentAcademicLevelsProgress) => {
      this.studentProgress = studentAcademicLevelsProgress;
      this.academicLevelProgresses = studentAcademicLevelsProgress.academicLevelProgresses;
      this.dataSource.data = studentAcademicLevelsProgress.academicLevelProgresses;
      this.studentAcademicLevelsCompletionAvarage = (studentAcademicLevelsProgress.completedLevels / studentAcademicLevelsProgress.requiredLevels) * 100;

    });
  }

  editStatus(academicLevelProgress: AcademicLevelProgress) {
    this.dialogService.open(ChangeAcademicLevelProgressStatusComponent, {
      context: {
        title: 'Change Academic Level Progress Status',
        student: this.student,
        academicLevelProgress: academicLevelProgress,
      },
    }).onClose.subscribe(result => {
      if (result.confirmed) {
        const studentAcademicLevelsProgress: StudentAcademicLevelsProgress =  result.studentAcademicLevelsProgress;
        this.studentProgress = studentAcademicLevelsProgress;
        this.academicLevelProgresses = studentAcademicLevelsProgress.academicLevelProgresses;
        this.dataSource.data = studentAcademicLevelsProgress.academicLevelProgresses;
      }
    });
  }

  preventDefaultEvent(event: any) {
    event.preventDefault();
  }

  getCompletionStatus(completionStatus: string): string {
    return this.statusService.getCompletionStatusClass(completionStatus);
  }

  enrollNewAcademicLevel() {
    this.dialogService.open(AcademicLevelStudentEnrollmentCreateComponent, {
      context: {
        title: 'Enroll New Academic Level',
        action: 'create',
        student: this.student,
      },
    }).onClose.subscribe(result => {
      if (result.confirmed) {

      }
    });
  }
}
