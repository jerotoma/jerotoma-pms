import { Component, OnInit, Input } from '@angular/core';
import {
  StudentAcademicLevelsProgressService,
  StatusService,
  AcademicLevelService,
 } from 'app/services';
import {
  Student,
  StudentAcademicLevelsProgress,
  AcademicLevel,
  StudentAcademicLevel,
} from 'app/models';

@Component({
  selector: 'app-student-academic-levels-progress',
  templateUrl: './student-academic-levels-progress.component.html',
  styleUrls: ['./student-academic-levels-progress.component.scss'],
})
export class StudentAcademicLevelsProgressComponent implements OnInit {

    @Input('student') student: Student;

    studentAcademicLevels: StudentAcademicLevel[] = [];
    academicLevels: AcademicLevel[] = [];
    studentProgress: StudentAcademicLevelsProgress;
    studentAcademicLevelsCompletionAvarage: number = 0;

  constructor(
    private academicLevelService: AcademicLevelService,
    private statusService: StatusService,
    private studentAcademicLevelsProgressService: StudentAcademicLevelsProgressService) { }

  ngOnInit(): void {
    this.loadAcademicLevelsByProgramId();
  }

  loadAcademicLevelsByProgramId() {
    this.studentAcademicLevelsProgressService
    .findStudentAcademicLevelsProgressByStudentId(this.student.id)
    .subscribe((studentProgress: StudentAcademicLevelsProgress) => {
      this.studentProgress = studentProgress;
      this.studentAcademicLevelsCompletionAvarage = (studentProgress.completedLevels / studentProgress.requiredLevels) * 100;

    });
    this.academicLevelService.loadAcademicLevelsByProgramId(this.student.programId).subscribe((academicLevels: AcademicLevel[]) => {
      this.academicLevels = academicLevels;
    });
  }

  preventDefaultEvent(event: any) {
    event.preventDefault();
  }

  getCompletionStatus(completionStatus: string): string {
    return this.statusService.getCompletionStatusClass(completionStatus);
  }
}
