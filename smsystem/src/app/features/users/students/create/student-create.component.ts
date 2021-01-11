import { Component, OnInit, ViewChild, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { AddressComponent, UserLoginInputComponent } from 'app/shared';
import { Student, Parent } from 'app/models/users';
import {
  Address,
  AddressWrapper,
  UserLoginInput,
  UserLoginInputWrapper,
  ResponseWrapper,
  Program,
  AcademicLevel,
  AcademicYear,
  ParentWrapper } from 'app/models';
import { PositionService, UserService, ModalService, ProgramService, AcademicLevelService } from 'app/services';
import { AcademicDisciplineService } from 'app/services/academic-disciplines';
import { QueryParam , DateValidator, DateFormatter, USER_TYPE, APP_ACTION_TYPE } from 'app/utils';
import { ShowMessage } from 'app/models/messages/show-message.model';


@Component({
  selector: 'app-student-create',
  templateUrl: 'student-create.component.html',
  styleUrls: ['student-create.component.scss'],
})
export class StudentCreateComponent implements OnInit, AfterViewInit {
  title: string = 'Create New Student';
  @Output() onUserCreationSuccess = new EventEmitter();
  @ViewChild(AddressComponent, {static: false}) appAddress: AddressComponent;
  @ViewChild(UserLoginInputComponent, {static: false}) appPassword: UserLoginInputComponent;
  userType: string = USER_TYPE.STUDENT;
  action: string = 'create';
  linearMode: boolean = true;
  studentForm: FormGroup;
  parentForm: FormGroup;
  addressForm: FormGroup;
  userLoginInput: UserLoginInput;
  student: Student;
  address: Address;
  parent: Parent;
  parents: Parent[] = [];
  selectedParents: Parent[] = [];
  academicLevels: AcademicLevel[] = [];
  academicYears: AcademicYear[] = [];
  programs: Program[] = [];
  parentIds: number[]  = [];
  userId: number;
  parentFullName: string;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  listDisplay: string = 'none';

  constructor(
    protected programService: ProgramService,
    protected academicLevelService: AcademicLevelService,
    protected positionService: PositionService,
    protected academicDisciplineService: AcademicDisciplineService,
    protected ref: NbDialogRef<StudentCreateComponent>,
    private userService:  UserService,
    private modalService: ModalService,
    private formBuilder: FormBuilder,
    ) {}

  ngOnInit() {
    this.loadStudentForm();
    this.loadParentForm();
    this.onCredentialInputChanges();
    this.loadPrograms();
  }
  ngAfterViewInit() {
    if (this.action === APP_ACTION_TYPE.edit) {
      this.loadStudent(this.userId);
    }
  }
  dismiss() {
    this.ref.close({
      confirmed: true,
    });
  }

  onSubmitStudentForm() {
   if (!this.studentForm.valid ) {
      return;
    }
    this.student = this.studentForm.value;
  }

  onSubmitParentForm() {
    if (!this.parentForm.valid ) {
      return;
    }
    this.parent = this.parentForm.value;
  }

  onSubmit() {
   if (!this.studentForm.valid && !this.parentForm.valid) {
      return;
    }
    window.console.log(this.studentForm.value);
    this.postData(this.studentForm.value);
  }

  postData(data: Student) {
      this.userService.addUser(data).subscribe((resp: ResponseWrapper) => {
        if (resp && resp.success) {
          this.modalService.openSnackBar('New Student has been created', 'success');
          this.resetForms();
        }
      });
  }
  updateData(data: Student) {
      this.userService.updateUser(data).subscribe((resp: ResponseWrapper) => {
        if (resp && resp.success) {
          this.modalService.openSnackBar('Student has been updated', 'success');
          this.resetForms();
        }
    });
  }

  resetForms() {
    this.studentForm.reset();
    if (this.action === APP_ACTION_TYPE.create) {
      this.appPassword.resetForm();
    }
    this.dismiss();
  }

  loadStudentForm() {
    this.studentForm = this.formBuilder.group({
      id: [null],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      studentNumber: [null],
      middleNames: [null],
      email: ['',
        Validators.compose([
          Validators.required,
          Validators.email,
        ]),
      ],
      phoneNumber: ['', Validators.required],
      userLoginInput: [null],
      gender: ['', Validators.required],
      picture: [''],
      userType: [USER_TYPE.STUDENT],
      birthDate: ['', DateValidator()],
      address: [null, Validators.required],
      parentFullName: [''],
      selectedParents: [''],
      userId: [null],
      parent: [null],
      programId: [null, Validators.required],
      academicLevelId: [null, Validators.required],
    });

    this.studentForm.get('programId').valueChanges.subscribe(programId => {
      if (programId) {
        this.loadAcademicLevelByProgram(programId);
      }
    });
  }

  onParentFormChanges(parentWrapper: ParentWrapper) {
    if (!parentWrapper) {
      return;
    }
    if (!parentWrapper.valid) {
      this.parentForm.controls['parent'].setErrors({ invalidAddress: true });
    } else {
      this.parentForm.controls['parent'].setErrors(null);
      this.studentForm.patchValue({parent: parentWrapper.parent});
      this.parentForm.patchValue({parent: parentWrapper.parent});
    }

  }

  loadParentForm() {
    this.parentForm = this.formBuilder.group({
      id: [null],
      parent: ['', Validators.required],
    });
  }

  getParam(): QueryParam {
    return {
      page: 1,
      pageSize: 10,
      orderby: 'DESC',
      status: '',
      search: '',
      fieldName: '',
      userType: USER_TYPE.STUDENT,
    };
  }

  loadStudent(userId: number) {
    this.userService.loadUser(userId).subscribe((student: Student) => {
       if (student) {
        this.student = student;
        this.updateUseInput();
      }
    }, error => {
      this.showMessage.error = true;
      this.showMessage.success = false;
      this.showMessage.message = error ? error.error.message : '';
    });
  }

  onStudentAddressChange(addressWrapper: AddressWrapper ) {
    if (!addressWrapper.isValid) {
      this.studentForm.controls['address'].setErrors({ invalidAddress: true });
    } else {
      this.studentForm.controls['address'].setErrors(null);
      this.studentForm.patchValue({address: addressWrapper.address});

    }
  }

  onUserLoginInputChange(userLoginInputWrapper: UserLoginInputWrapper) {
    if (userLoginInputWrapper.canUserLogin) {
      if (userLoginInputWrapper.isValid) {
        this.userLoginInput = userLoginInputWrapper.userLoginInput;
        this.studentForm.patchValue({
          userLoginInput: userLoginInputWrapper.userLoginInput,
        });
        this.studentForm.controls['userLoginInput'].setErrors(null);
      } else {
        this.studentForm.controls['userLoginInput'].setErrors({ invalidAddress: true });
      }
    }
  }

  pickUser(parent: Parent) {
    let parentFound = false;
    this.listDisplay = 'none';
    if (this.selectedParents.length === 0) {
      this.selectedParents.push(parent);
    } else {
        this.selectedParents.forEach(p => {
          if (p.id === parent.id) {
            parentFound = true;
          }
      });
      if (!parentFound) {
        this.selectedParents.push(parent);
      }
    }
    this.studentForm.patchValue({
      selectedParents: this.parentIds,
      parentFullName: '',
    });
  }
  search(value: string) {
    const param = this.getParam();
    param.search = value;
    param.userType = USER_TYPE.PARENT;
    this.userService.search(param).subscribe((users: Parent[]) => {
      this.parents = [];
      if (users) {
        this.parents = users;
        this.listDisplay = 'block';
      }
    });
  }
  onCredentialInputChanges() {
    this.studentForm.get('parentFullName').valueChanges.subscribe(value => {
      if (value) {
        this.search(value);
      }
    });
  }

  selectedParentChanged(event: any, parent: Parent) {
    if (!event) {
      for (let i = 0; i < this.selectedParents.length; i++) {
        if (this.selectedParents[i].id === parent.id) {
          this.selectedParents.splice(i, 1);
        }
     }
    } else {
      this.selectedParents.push(parent);
    }
  }

  updateUseInput() {
    this.studentForm.patchValue({
      id: this.student.id,
      firstName: this.student.firstName,
      lastName: this.student.lastName,
      position: this.student.position ? this.student.position.id : null,
      occupation: this.student.occupation ? this.student.occupation : 'Student',
      employmentCode: this.student.userCode,
      gender: this.student.gender,
      picture: this.student.picture,
      userId: this.student.userId,
      middleNames: this.student.middleNames,
      phoneNumber: this.student.phoneNumber,
      email: this.student.emailAddress,
      birthDate: DateFormatter(this.student.birthDate, 'YYYY/MM/DD', false),
      userType: USER_TYPE.STUDENT,
      academicDiscipline: this.student.academicDiscipline ? this.student.academicDiscipline.id : null,
      fullName: this.student.fullName,
      address: this.student.address ? this.student.address : null,
      programId: this.student.programId,
      academicLevelId: this.student.academicLevelId,
    });

    if (this.student.address) {
      this.appAddress.patchAddressValue(this.student.address);
    }
  }

  loadAcademicLevelByProgram(programId: number) {
    this.academicLevels = [];
    this.academicLevelService.loadAcademicLevelsByProgramId(programId).subscribe((academicLevels: AcademicLevel[]) => {
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
