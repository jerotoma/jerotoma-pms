
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormArray, FormControl } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import {
  StudentClassService,
  ClassService,
  AcademicYearService,
  ProgramService,
  AcademicLevelService,
  StudentAcademicLevelService,
  UserService,
  ModalService,
 } from 'app/services';
import { QueryParam, USER_TYPE } from 'app/utils';
import {
  ShowMessage,
  ClassView,
  StudentClassAdmission,
  Student,
  AcademicYear,
  StudentClass,
  AcademicLevel,
  Program,
} from 'app/models';

@Component({
  selector: 'app-student-academic-level-enrollment-create',
  styleUrls: ['./student-academic-level-enrollment-create.component.scss'],
  templateUrl: './student-academic-level-enrollment-create.component.html',
})
export class StudentCourseEnrollmentCreateComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = 'create';
  @Input() studentId: string = '0';
  @Output() onCreationSuccess = new EventEmitter();

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
  academicYear: AcademicYear;
  studentClassAdmission: StudentClassAdmission;

  param: QueryParam =  {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType:  this.userType,
  };

  jClasses: ClassView[];
  academicYears: AcademicYear[];
  student: Student;
  students: Student[];
  studentClassForm: FormGroup;
  academicLevel: AcademicLevel;
  academicLevels: AcademicLevel[];
  programs: Program[];
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  listDisplay: string = 'none';

  constructor(
    private modalService: ModalService,
    private userService: UserService,
    private programService: ProgramService,
    private academicLevelService: AcademicLevelService,
    private academicYearService: AcademicYearService,
    private classService: ClassService,
    private studentClassService: StudentClassService,
    private studentAcademicLevelService: StudentAcademicLevelService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<StudentCourseEnrollmentCreateComponent>) {}

  ngOnInit() {
    this.loadData();
    this.loadForm();
    this.loadPrograms();
  }

  dismiss() {
    this.ref.close({
      confirmed: this.confirmed,
    });
  }

  checkAllStudentBoxes(checked: boolean) {
    this.checkAllStudents = checked;
  }

  checkAllCourseBoxes(checked: boolean) {
    this.checkAllCourses = checked;
  }

  setSelectedUser(student: Student) {
    this.studentClassForm.patchValue({
      studentId: student.id,
    });
    this.programId = student.programId;
    this.loadAvailableAcademicLevelsByStudentId(student.id);
  }

  onSubmit() {
    this.confirmed = false;
    this.studentClassAdmission = this.studentClassForm.value;
    this.studentClassService.createStudentClass(this.studentClassAdmission)
    .subscribe((result: StudentClassAdmission) => {
      const resp = result;
      this.confirmed = true;
      this.studentClassForm.reset();
      this.dismiss();
    });
  }

  checkedChange(checked: boolean, jClass: ClassView) {
    if (checked) {
      this.jClassIds.push(jClass.id);
    } else {
      for (let i = 0; i < this.jClassIds.length; i++) {
        if ( this.jClassIds[i] === jClass.id) {
          this.jClassIds.splice(i, 1);
        }
     }
    }
    this.studentClassForm.patchValue({
      jClassIds: this.jClassIds,
    });
  }

  loadForm() {
    this.studentClassForm = this.formBuilder.group({
      id: [null],
      studentId: ['', Validators.required],
      academicYearId: ['', Validators.required],
      academicLevelId: ['', Validators.required],
      jClassIds: [[], Validators.required],
    });
    this.onChanges();
  }

  onChanges() {
    this.studentClassForm.get('academicLevelId').valueChanges.subscribe((academicLevelId: number) => {
      const programId = this.programId;
      this.academicYearId = null;
      this.studentClassForm.patchValue({
        academicYearId: null,
      }, {emitEvent: false});
      if (academicLevelId && programId) {
        this.academicLevelId = academicLevelId;
        this.jClasses = [];
        this.loadStudentsByProgramAndAcademicLevelIDs(academicLevelId, programId);
      }
    });
    this.studentClassForm.get('academicYearId').valueChanges.subscribe((academicYearId: number) => {
      if (academicYearId != null) {
        this.academicYears.forEach(academicYear => {
          if (academicYear.id === academicYearId) {
            this.academicYear = academicYear;
          }
        });
        this.loadJClassesByAcademicYear(academicYearId);
      }
    });
  }

  loadData() {
    this.loadAcademicYears();
  }

  loadStudentsByProgramAndAcademicLevelIDs(academicLevelId: number, programId: number) {
    this.studentAcademicLevelService.loadStudentsWhoAreUnenrolledAndQualifiedForThisProgramAndAcademicLevel(academicLevelId, programId).subscribe((students: Student[]) => {
      this.students = students;
      this.isLoading = false;
    });
  }

  loadJClassesByAcademicYear(academicYearId: number) {
    if (!this.programId || !this.academicLevelId) {
        this.studentClassForm.patchValue({
          academicYearId: null,
        }, {emitEvent: false});
        this.modalService.openSnackBar('Program or Academic Level can not be empty', 'info');
        return;
    }
    this.isLoading = true;
    this.classService.loadJClassesByParams(this.programId, this.academicLevelId, academicYearId).subscribe((jClassViews: ClassView[]) => {
      this.jClasses = jClassViews;
      this.isLoading = false;
    });
  }
  loadAcademicYears() {
    this.academicYearService.getAcademicYears()
    .subscribe((academicYears: AcademicYear[]) => {
      if (academicYears) {
        this.academicYears = academicYears;
      }
    });
  }
  loadStudentClass(studentClassId: number) {
    this.studentClassService.getStudentClass(studentClassId).subscribe((studentClass: StudentClass) => {
      this.student = studentClass.student;
      this.academicYear = studentClass.academicYear;
      this.loadJClassesByAcademicYear(studentClass.academicYear.id);
      this.jClasses = studentClass.jClasses;
      this.studentClassForm.patchValue({
        id: studentClass.id,
        studentId: this.student.id,
        academicYearId: this.academicYear.id,
        jClassIds: this.pushJClasses(this.jClasses),
      }, {emitEvent: false});
    });
  }
  patchStudent(student: Student) {
    this.student = student;
    this.studentClassForm.patchValue({
      studentId: student.id,
    });
  }

  pushJClasses(jClasses: ClassView[]) {
    const jClassesIds  = [];
    jClasses.forEach((jClass) => {
      jClassesIds.push(jClass.id);
    });
    return jClassesIds;
  }

  loadAvailableAcademicLevelsByStudentId(studentId: number) {
    this.academicLevels = [];
    this.academicLevelService.loadAvailableAcademicLevelsByStudentId(studentId)
    .subscribe((academicLevels: AcademicLevel[] ) => {
      if (academicLevels) {
        this.academicLevels = academicLevels;
      }
    });
  }

  loadPrograms() {
    this.programService.loadProgramList()
      .subscribe((programs: Program[]) => {
        if (programs) {
          this.programs = programs;
        }
      });
  }
}
