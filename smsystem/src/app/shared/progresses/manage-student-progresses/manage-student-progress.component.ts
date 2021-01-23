import { Component, OnInit, Input, ViewChild  } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormArray, FormControl } from '@angular/forms';

import { PageEvent } from '@angular/material/paginator';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, MatSortable } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

import { NbDialogRef } from '@nebular/theme';
import { NbDialogService } from '@nebular/theme';

import { ManageStudentProgressCreateComponent } from './manage-student-progress-create/manage-student-progress-create.component';

import {
  StudentAcademicLevelService,
  ClassService,
  AcademicYearService,
  ProgramService,
  AcademicLevelService,
  UserService,
  ModalService,
 } from 'app/services';
import { USER_TYPE } from 'app/utils';
import {
  ClassView,
  Teacher,
  AcademicYear,
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
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  academicYearId: number;
  programId: number;
  academicLevelId: number;
  teacherId: number;
  isLoading: boolean = false;

  classParam: TeacherClassParam;
  academicYear: AcademicYear;
  academicYears: AcademicYear[];
  academicLevel: AcademicLevel;
  academicLevels: AcademicLevel[];
  programs: Program[];
  jClasses: ClassView[];
  manageStudentProgressForm: FormGroup;

  hidePageSize: boolean = false;
  pageSize: number = 10;
  totalNumberOfItems: number = 20;
  pageSizeOptions: number[] = [10, 20, 30, 50, 70, 100];
  displayedColumns: string[] = ['id', 'course', 'totalStudents', 'academicYearTerm', 'academicLevel', 'program', 'department', 'action'];
  sortableColumns: MatSortable[] = [
    {
      id: 'course',
      disableClear: true,
      start: 'asc',
    },
    {
      id: 'academicLevel',
      disableClear: true,
      start: 'asc',
    },
    {
      id: 'academicYearTerm',
      disableClear: true,
      start: 'asc',
    },
    {
      id: 'program',
      disableClear: true,
      start: 'asc',
    },
    {
      id: 'department',
      disableClear: true,
      start: 'asc',
    },
  ];
  dataSource: MatTableDataSource<ClassView> = new MatTableDataSource<ClassView>();

  constructor(
    private modalService: ModalService,
    private programService: ProgramService,
    private academicLevelService: AcademicLevelService,
    private academicYearService: AcademicYearService,
    private dialogService: NbDialogService,
    private formBuilder: FormBuilder,
    private classService: ClassService) {}

  ngOnInit(): void {
    this.isLoading = true;
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.loadForm();
    this.loadData();
  }

  loadData() {
    this.loadPrograms();
    this.loadAcademicYears();
    this.loadJClassesByTeacherID();
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
     if (this.programId !== programId)  {
      this.loadAcademicLevelsByProgramId(programId);
     }
    });

    this.manageStudentProgressForm.get('academicLevelId').valueChanges.subscribe((academicLevelId: number) => {
      if (this.academicLevelId !== academicLevelId)  {
        this.loadJClassesByParams(this.programId, academicLevelId, this.academicYearId);
      }
    });
  }

  loadAcademicLevelsByProgramId(programId: number) {
    this.isLoading = true;
    this.academicLevels = [];
    this.programId = programId;
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
  loadJClassesByTeacherID() {
    this.isLoading = true;
    this.jClasses = [];
    this.classService.loadClassesByUserId(this.teacher.userId).subscribe((classViews: ClassView[]) => {
      this.jClasses = classViews;
      this.dataSource.data = classViews;
      this.dataSource.sort = this.sort;
      this.isLoading = false;
    });
  }
  loadJClassesByParams(programId: number, academicLevelId: number, academicYearId: number) {
    this.academicLevelId = academicLevelId;
    if (!programId || !academicLevelId || !academicYearId) {
        this.modalService.openSnackBar('Program or Academic Level can not be empty', 'info');
        return;
    }
    this.isLoading = true;
    this.classParam = {
      teacherId: this.teacher.id,
      programId: programId,
      academicLevelId: academicLevelId,
      academicYearId: academicYearId,
    };
    this.jClasses = [];
    this.classService.loadClassesByTeacherClassParam(this.classParam).subscribe((classViews: ClassView[]) => {
      if (classViews && classViews.length === 0) {
        this.modalService.openSnackBar('No classes available for the selected options', 'info');
      }
      this.jClasses = classViews;
      this.isLoading = false;
    });
  }

  manageProgress(classView: ClassView) {
    this.dialogService.open(ManageStudentProgressCreateComponent, {
      context: {
        title: 'Manage Students Progress',
        action: 'create',
        classView: classView,
      },
    }).onClose.subscribe(result => {
      if (result.confirmed) {

      }
    });
  }

  onPageChange(event: any) {

  }

  addMoreStudent(classView: ClassView) {

  }

  removeStudent(classView: ClassView) {

  }
}
