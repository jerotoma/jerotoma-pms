import { Component, OnInit, Input } from '@angular/core';
import { NbDialogService } from '@nebular/theme';
import { AcademicLevelStudentEnrollmentCreateComponent } from 'app/shared/enrollments/academic-levels';
import {
  StudentProgressService,
  StatusService,
 } from 'app/services';
import {
  Student,
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
    studentAcademicLevelsCompletionAvarage: number = 0;

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
    .subscribe((studentProgress: StudentAcademicLevelsProgress) => {
      this.studentProgress = studentProgress;
      this.studentAcademicLevelsCompletionAvarage = (studentProgress.completedLevels / studentProgress.requiredLevels) * 100;

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
