import { Component, OnInit, Input } from '@angular/core';
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

    studentProgress: StudentAcademicLevelsProgress;
    studentAcademicLevelsCompletionAvarage: number = 0;

  constructor(
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
}
