
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import {  ThemePalette } from '@angular/material/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef } from '@nebular/theme';
import {
  StudentAcademicLevelService,
  ClassService,
  ModalService,
  AcademicYearService,
  AcademicLevelService,
 } from 'app/services';
import {
  ShowMessage,
  ClassView,
  StudentClassAdmission,
  Student,
  AcademicYear,
  AcademicLevel,
  StudentAcademicLevel,
} from 'app/models';
import { QueryParam, USER_TYPE, OPEN_CLOSE_ANIMATION } from 'app/utils';

@Component({
  selector: 'app-student-academic-level-classes-enrollment-edit',
  animations: OPEN_CLOSE_ANIMATION,
  styleUrls: ['./student-academic-level-classes-enrollment-edit.component.scss'],
  templateUrl: './student-academic-level-classes-enrollment-edit.component.html',
})
export class StudentAcademicLevelClassesEnrollmentEditComponent implements OnInit {
  @Input() title: string;
  @Input() studentAcademicLevel: StudentAcademicLevel;
  @Output() onCreationSuccess = new EventEmitter();

  userType: string = USER_TYPE.STUDENT;
  checkboxColor: ThemePalette = 'warn';

  academicYearId: number;
  courseId: number;
  jClassIds: number[] = [];
  teacherId: number;
  isLoading: boolean = false;
  confirmed: boolean = false;
  isAddMore: boolean = false;
  academicYear: AcademicYear;
  academicLevel: AcademicLevel;
  academicLevels: AcademicLevel[];
  studentClassAdmission: StudentClassAdmission;

  param: QueryParam =  {
    page: 1,
    pageSize: 10,
    orderby: 'DESC',
    status: '',
    search: '',
    fieldName: '',
    userType: this.userType,
  };

  registeredClasses: ClassView[];
  classViews: ClassView[];
  academicYears: AcademicYear[];
  student: Student;
  students: Student[];
  studentAcademicLevelForm: FormGroup;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  listDisplay: string = 'none';

  constructor(
    private academicYearService: AcademicYearService,
    private academicLevelService: AcademicLevelService,
    private classService: ClassService,
    private modalService: ModalService,
    private studentClassService: StudentAcademicLevelService,
    private formBuilder: FormBuilder,
    protected ref: NbDialogRef<StudentAcademicLevelClassesEnrollmentEditComponent>) {}

  ngOnInit() {
    this.loadData();
    this.loadForm();
    if (this.studentAcademicLevel) {
      this.loadStudentClasses(this.studentAcademicLevel);
    }
  }

  dismiss() {
    this.ref.close({
      confirmed: this.confirmed,
    });
  }

  onSubmit() {
    this.confirmed = false;
    this.studentClassAdmission = this.studentAcademicLevelForm.value;
    this.updateStudentClass();
  }
  updateStudentClass() {
    this.studentClassService.updateStudentAcademicLevelClasses(this.studentClassAdmission)
          .subscribe((result: StudentClassAdmission) => {
            if (result) {
              const resp = result;
              this.confirmed = true;
              this.studentAcademicLevelForm.reset();
              this.dismiss();
              this.modalService.openSnackBar('Student Class has been updated', 'success');
            }
          });
  }
  removeJClass(event: any, classView: ClassView, isRemoveJClass: boolean) {
    event.preventDefault();
    event.stopPropagation();
    if (isRemoveJClass) {
      this.checkedChange(false, classView);
      for (let i = 0; i < this.registeredClasses.length; i++) {
        if ( this.registeredClasses[i].id === classView.id) {
          this.registeredClasses.splice(i, 1);
          this.deleteStudentClass(classView.id);
        }
     }
    }
  }

  deleteStudentClass(classId: number) {
    const data = {
      studentId: this.student.id,
      academicLevelId: this.student.academicLevelId,
      academicYearId: this.academicYear.id,
      jClassId: classId,
    }
    this.studentClassService.deleteStudentClass(data).subscribe((isDeleted: boolean) => {
      if (isDeleted) {
        this.modalService.openSnackBar('Student Class has been deleted', 'success');
      }
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
    this.studentAcademicLevelForm.patchValue({
      jClassIds: this.jClassIds,
    });
  }
  loadForm() {
    this.studentAcademicLevelForm = this.formBuilder.group({
      id: [null],
      academicYearId: ['', Validators.required],
      academicLevelId: ['', Validators.required],
      jClassIds: [[], Validators.required],
      studentId: ['', Validators.required],
      fullName: ['', Validators.required],
    });
    this.onChanges();
  }

  onChanges() {
    this.studentAcademicLevelForm.get('academicLevelId').valueChanges.subscribe((academicLevelId: number) => {
      if (academicLevelId) {
        this.setCurrentAcademicLevel(academicLevelId);
      }
    });

    this.studentAcademicLevelForm.get('academicYearId').valueChanges.subscribe((academicYearId: number) => {
      if (academicYearId != null) {
        this.academicYears.forEach(academicYear => {
          if (academicYear.id === academicYearId) {
            this.academicYear = academicYear;
          }
        });
      }
    });
  }
  addMoreCourses() {
    this.isAddMore = true;

    if (this.academicYear && this.academicLevel) {
      this.loadUnregisteredClassesByStudent(this.student.id, this.academicLevel.id, this.academicYear.id);
    } else {
      this.modalService.openSnackBar('Academic Year or Level must have a value to continue', 'info');
    }
  }
  loadData() {
    this.loadAcademicYears();
  }

  loadAcademicLevelsByProgramId(programId: number) {
    this.academicLevels = [];
    this.isLoading = true;
    this.academicLevelService.loadAcademicLevelsByProgramId(programId)
    .subscribe((academicLevels: AcademicLevel[] ) => {
      if (academicLevels) {
        this.academicLevels = academicLevels;
        this.setCurrentAcademicLevel(this.studentAcademicLevel.student.academicLevelId);
        this.studentAcademicLevelForm.patchValue({
          academicLevelId: this.studentAcademicLevel.student.academicLevelId,
        }, {emitEvent: false});
      }
      this.isLoading = false;
    });
  }

  setCurrentAcademicLevel(academicLevelId: number) {
    this.academicLevels.forEach(academicLevel => {
      if (academicLevel.id === academicLevelId) {
        this.academicLevel = academicLevel;
      }
    });
  }

  loadUnregisteredClassesByStudent(studentId: number, academicLevelId: number, academicYearId: number) {
    this.isLoading = true;
    this.classService.loadUnregisteredClassesByStudent(studentId, academicLevelId, academicYearId).subscribe((jClassViews: ClassView[]) => {
      if (jClassViews && jClassViews.length > 0) {
        this.classViews = jClassViews;
      } else {
        this.modalService.openSnackBar('No more classes were found', 'success');
      }
      this.isLoading = false;
    });
  }

  loadJClassesByAcademicYear(academicYearId: number) {
    this.isLoading = true;
    this.classService.loadJClassesByAcademicYear(academicYearId).subscribe((jClassViews: ClassView[]) => {
      this.classViews = jClassViews;
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

  loadStudentClasses(sc: StudentAcademicLevel) {
    this.isLoading = false;
    this.studentClassService.getStudentAcademicLevel(sc.id).subscribe((studentAcademicLevel: StudentAcademicLevel) => {
      if (studentAcademicLevel) {
        this.loadAcademicLevelsByProgramId(studentAcademicLevel.student.programId);
        this.student = studentAcademicLevel.student;
        this.academicYear = studentAcademicLevel.academicYear;
        this.registeredClasses = studentAcademicLevel.jClasses;
        this.jClassIds =  this.pushJClasses(this.registeredClasses),
        this.studentAcademicLevelForm.patchValue({
          id: studentAcademicLevel.id,
          academicYearId: studentAcademicLevel.academicYear.id,
          studentId: this.student.id,
          fullName: this.student.fullName,
          jClassIds: this.jClassIds,
        }, {emitEvent: false});
      }
      this.isLoading = true;
    });
  }
  patchStudent(student: Student) {
    this.student = student;
    this.studentAcademicLevelForm.patchValue({
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

  removeItemFromArray(jClasses: ClassView[], jClass: ClassView) {
    for (let i = 0; i < this.classViews.length; i++) {
      if ( this.classViews[i].id === jClass.id) {
        this.classViews.splice(i, 1);
      }
   }
   return jClasses;
  }
}
