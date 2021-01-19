import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import {
  StudentAcademicLevelService,
  ClassService,
  StatusService,
  AcademicLevelService,
 } from 'app/services';
import { USER_TYPE, QueryParam } from 'app/utils';
import {
  ClassView,
  Student,
  StudentProgress,
  AcademicLevel,
  StudentAcademicLevel,
  CompletionStatus,
} from 'app/models';

@Component({
  selector: 'app-my-progress',
  templateUrl: './progress.component.html',
  styleUrls: ['./progress.component.scss'],
})
export class ProgressComponent implements OnInit {

    @Input('student') student: Student;

    studentAcademicLevels: StudentAcademicLevel[] = [];
    academicLevels: AcademicLevel[] = [];
    studentProgress: StudentProgress;
    studentAcademicLevelsCompletionAvarage: number = 0;

  constructor(
    private academicLevelService: AcademicLevelService,
    private classService: ClassService,
    private statusService: StatusService,
    private studentAcademicLevelService: StudentAcademicLevelService,
    private formBuilder: FormBuilder) { }



  ngOnInit(): void {
    this.loadAcademicLevelsByProgramId();
  }

  loadAcademicLevelsByProgramId() {
    this.studentAcademicLevelService.loadStudentProgressByStudentId(this.student.id).subscribe((studentProgress: StudentProgress) => {
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
