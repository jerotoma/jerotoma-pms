import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormArray, FormControl } from '@angular/forms';
import { NbDialogRef } from '@nebular/theme';
import {
  StudentAcademicLevelService,
  ClassService,
  AcademicYearService,
  ProgramService,
  AcademicLevelService,
  UserService,
  ModalService,
 } from 'app/services';
import { QueryParam, USER_TYPE, APP_CONSTANTS } from 'app/utils';
import {
  ShowMessage,
  ClassView,
  StudentClassAdmission,
  Student,
  Teacher,
  AcademicYear,
  StudentAcademicLevel,
  AcademicLevel,
  TeacherClassParam,
  Program,
} from 'app/models';

@Component({
  selector: 'app-manage-student-progress',
  templateUrl: './manage-student-progress.component.html',
  styleUrls: ['./manage-student-progress.component.scss'],
})
export class ManageStudentProgressComponent implements OnInit {

  @Input() title: string;
  @Input() action: string = 'create';
  @Input('teacher') teacher: Teacher = null;
  currentAcademicYearKey: string = APP_CONSTANTS.currentAcademicYear;

  userType: string = USER_TYPE.STUDENT;

  academicYearId: number;
  courseId: number;
  programId: number;
  academicLevelId: number;
  jClassIds: number[] = [];
  teacherId: number;
  isLoading: boolean = false;
  confirmed: boolean = false;
  checkAllStudents: boolean = false;
  checkAllCourses: boolean = false;

  classParam: TeacherClassParam
  academicYear: AcademicYear;
  academicYears: AcademicYear[];
  currentAcademicYear: AcademicYear = null;
  academicLevel: AcademicLevel;
  academicLevels: AcademicLevel[];
  programs: Program[];
  jClasses: ClassView[];
  studentClassAdmission: StudentClassAdmission;
  manageStudentProgressForm: FormGroup;

  constructor(
    private modalService: ModalService,
    private programService: ProgramService,
    private academicLevelService: AcademicLevelService,
    private academicYearService: AcademicYearService,
    private formBuilder: FormBuilder,
    private classService: ClassService,
    private studentClassService: StudentAcademicLevelService,
    private studentAcademicLevelService: StudentAcademicLevelService) {}

  ngOnInit(): void {
    this.isLoading = true;
    this.loadForm();
    this.loadData();
  }

  loadData() {
    this.loadPrograms();
    this.loadAcademicYears();
  }

  loadForm() {
    this.manageStudentProgressForm = this.formBuilder.group({
      teacherId: [null, Validators.required],
      academicYearId: [null, Validators.required],
      academicLevelId: [null, Validators.required],
      programId: [null, Validators.required],
    });
    this.onChanges();
  }

  onChanges() {
    this.manageStudentProgressForm.get('academicYearId').valueChanges.subscribe((academicYearId: number) => {
      this.academicYearId = academicYearId;
      if (this.programId && this.academicLevelId) {
        this.loadJClassesByParams(this.programId, this.academicLevelId, academicYearId);
    }
     });

    this.manageStudentProgressForm.get('programId').valueChanges.subscribe((programId: number) => {
     if (this.programId != programId)  {
      this.loadAcademicLevelsByProgramId(programId);
     }
    });

    this.manageStudentProgressForm.get('academicLevelId').valueChanges.subscribe((academicLevelId: number) => {
      if (this.academicLevelId != academicLevelId)  {
        this.loadJClassesByParams(this.programId, academicLevelId, this.academicYearId);
      }
    });
  }

  loadAcademicLevelsByProgramId(programId: number) {
    this.isLoading = true;
    this.academicLevels = [];
    this.programId = programId
    this.manageStudentProgressForm.patchValue({
      academicLevelId: null,
    }, {emitEvent: false});
    this.academicLevelService.loadAcademicLevelsByProgramId(programId)
    .subscribe((academicLevels: AcademicLevel[] ) => {
      if (academicLevels) {
        this.academicLevels = academicLevels;
      }
      this.isLoading = false;
    });
  }

  loadPrograms() {
    this.isLoading = true;
    this.programService.loadProgramList()
      .subscribe((programs: Program[]) => {
        if (programs) {
          this.programs = programs;
        }
        this.isLoading = false;
      });
  }

  loadAcademicYears() {
    this.isLoading = true;
    this.academicYearService.getAcademicYears()
    .subscribe((academicYears: AcademicYear[]) => {
        if (academicYears) {
          this.academicYears = academicYears;
        }
        this.isLoading = false;
      });
  }

  loadJClassesByParams(programId: number, academicLevelId: number, academicYearId: number) {
    if (!programId || !academicLevelId || !academicYearId) {
        this.modalService.openSnackBar('Program or Academic Level can not be empty', 'info');
        return;
    }
    this.isLoading = true;
    this.academicLevelId = academicLevelId
    this.classParam = {
      teacherId: this.teacher.id,
      programId: programId,
      academicLevelId: academicLevelId,
      academicYearId: academicYearId,
    }
    this.classService.loadClassesByTeacherClassParam(this.classParam).subscribe((classViews: ClassView[]) => {
      if (classViews && classViews.length === 0) {
        this.modalService.openSnackBar('No classes available for the selected options', 'info');
      }
      this.jClasses = classViews;
      this.isLoading = false;
    });
  }
}
