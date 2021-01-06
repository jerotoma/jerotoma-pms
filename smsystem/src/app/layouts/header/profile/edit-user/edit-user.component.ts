import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDateService } from '@nebular/theme';
import { AddressComponent } from 'app/shared';
import {
  User,
  Position,
  AddressWrapper,
  AcademicDiscipline,
  ShowMessage,
  ResponseWrapper,
  UserLoginInput,
  UserLoginInputWrapper,
} from 'app/models';
import {
  UserService,
  PositionService,
  AcademicDisciplineService,
  ModalService,
} from 'app/services';

import { QueryParam , DateValidator, DateFormatter, USER_TYPE, APP_ACTION_TYPE } from 'app/utils';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss'],
})
export class EditUserComponent implements OnInit {
  @Input('userType') userType: string = USER_TYPE.TEACHER;
  @Input('user') user: User = null;
  @Output() onUserCreationSuccess = new EventEmitter();
  @ViewChild(AddressComponent, {static: false}) appAddress: AddressComponent;
  action: string = APP_ACTION_TYPE.edit;
  position: number;
  academicDiscipline: number;
  userLoginInput: UserLoginInput;

  userForm: FormGroup;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  users: User[] = [];
  positions: Position[] = [];
  academicDisciplines: AcademicDiscipline[] = [];
  listDisplay: string = 'none';

  constructor(
    protected positionService: PositionService,
    protected academicDisciplineService: AcademicDisciplineService,
    protected dateService: NbDateService<Date>,
    private userService:  UserService,
    private modalService: ModalService,
    private formBuilder: FormBuilder,
    ) {}

  ngOnInit() {
    this.loadPositionList();
    this.loadAcademicDisciplineList();
    this.loadForm();
    if (this.user) {
      this.loadUser(this.user);
    }
  }

  onSubmit() {
    this.showMessage.success = false;
    this.showMessage.error = false;
    this.updateUser(this.userForm.value);
  }
  updateUser(data: User) {
    this.userService.updateUser(data).subscribe((user: ResponseWrapper) => {
      if (user) {
        this.showMessage.success = true;
        this.onUserCreationSuccess.emit(this.showMessage.success);
        this.showMessage.error = false;
        this.openSnackBar('User has been updated', 'success');
      }
    });
  }

  loadForm() {
    this.userForm = this.formBuilder.group({
      id: [null],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      position: [''],
      occupation: [''],
      userCode: [''],
      gender: ['', Validators.required],
      picture: [''],
      middleNames: [null],
      phoneNumber: ['', Validators.required],
      userId: [null, Validators.required],
      birthDate: ['', Validators.required],
      userType: [null],
      academicDiscipline: [''],
      address: [null, Validators.required],
    });
  }

  loadUser(user: User) {
    this.position = user.position ? user.position.id : null;
    this.academicDiscipline = user.academicDiscipline ? user.academicDiscipline.id : null;
    this.updateUserInput(user);
  }

  loadPositionList() {
    this.positionService.loadPositionList().subscribe((positions: Position[]) => {
      if (positions) {
        this.positions = positions;
        this.updateUserInput(this.user);
      }
    });
  }
  loadAcademicDisciplineList() {
    this.academicDisciplineService.loadAcademicDisciplineList().subscribe((academicDisciplines: AcademicDiscipline[] ) => {
      if (academicDisciplines) {
        this.showMessage.error = false;
        this.academicDisciplines = academicDisciplines;
      }
    });
  }

  onAddressChange(addressWrapper: AddressWrapper ) {
    if (!addressWrapper.isValid) {
      this.userForm.controls['address'].setErrors({ invalidAddress: true });
    } else {
      this.userForm.controls['address'].setErrors(null);
      this.userForm.patchValue({address: addressWrapper.address});

    }
  }
  getParam(): QueryParam {
    return {
      page: 1,
      pageSize: 10,
      orderby: 'DESC',
      status: '',
      search: '',
      fieldName: '',
      userType: 'parent',
    };
  }

  openSnackBar(message: string, panelClass: string) {
    this.modalService.openSnackBar(message, panelClass);
  }

  updateUserInput(user: User) {
    this.userForm.patchValue({
      id: user.id,
      firstName: user.firstName,
      lastName: user.lastName,
      position: user.position ? user.position.id : null ,
      occupation: user.occupation,
      userCode: user.userCode,
      gender: user.gender,
      picture: user.picture,
      userId: user.userId,
      middleNames: user.middleNames,
      phoneNumber: user.phoneNumber,
      // birthDate: DateFormatter(user.birthDate),
      userType: user.userType,
      academicDiscipline: user.academicDiscipline ? user.academicDiscipline.id : null,
      address: user.address,
    });
    if (this.appAddress && user.address) {
      this.appAddress.patchAddressValue(user.address);
    }
   this.userLoginInput = {
     username: user.username,
     password: '',
     confirmPassword: '',
   };
  }
}
