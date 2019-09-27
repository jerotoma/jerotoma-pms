import { Component, OnInit, ViewChild, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { NbDialogRef} from '@nebular/theme';
import { AddressComponent } from 'app/shared';
import { Student, Parent } from 'app/models/users';
import { Address, AddressWrapper } from 'app/models/addresses';
import { UserService } from 'app/services/users';
import { PositionService } from 'app/services/positions';
import { AcademicDisciplineService } from 'app/services/academic-disciplines';
import { QueryParam , DateValidator, DateFormatter } from 'app/utils';
import { ShowMessage } from 'app/models/messages/show-message.model';


@Component({
  selector: 'app-parent-create',
  templateUrl: 'parent-create.component.html',
  styleUrls: ['parent-create.component.scss'],
})
export class ParentCreateComponent implements OnInit, AfterViewInit {
  title: string = 'Create New Parent';
  @Output() onUserCreationSuccess = new EventEmitter();
  @ViewChild(AddressComponent, {static: false}) appAddress: AddressComponent;
  action: string = 'create';

  parentForm: FormGroup;
  addressForm: FormGroup;
  student: Student;
  address: Address;
  students: Student[] = [];
  selectedStudents: Student[] = [];
  studentIds: number[]  = [];
  parent: Parent;
  studentId: string;
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  listDisplay: string = 'none';

  constructor(
    protected positionService: PositionService,
    protected academicDisciplineService: AcademicDisciplineService,
    protected ref: NbDialogRef<ParentCreateComponent>,
    private userService:  UserService,
    private formBuilder: FormBuilder,
    ) {}

  ngOnInit() {
    this.loadParentForm();
    this.onStudentFullInputChanges();
  }
  ngAfterViewInit() {
    if (this.action === 'edit') {
      // this.loadTeacher(parseInt(this.studentId, 10));
    }
  }
  dismiss() {
    this.ref.close();
  }
  onSubmit() {
    this.studentIds = [];
    // window.console.log(this.parentForm, this.parentForm);
   if (this.parentForm.valid ) {
      this.parent = this.parentForm.value;
      for (let i = 0; i < this.selectedStudents.length; i++) {
        this.studentIds.push(this.selectedStudents[i].id);
      }
      this.parent.studentIds = this.studentIds;
      this.postData(this.parent);
    }
  }

  postData(data: Parent) {
    this.showMessage.success = false;
    this.showMessage.error = false;
    if (this.action === 'edit') {
      this.updateData(data);
    } else {
      this.userService.addUser(data).subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
        const resp = result;
        const status = resp.status;
        if (status !== null && status === 200) {
          this.showMessage.success = true;
          this.showMessage.error = false;
          this.showMessage.message = resp ? resp.body.message : '';
          this.resetForms();
        } else {
          this.showMessage.success = false;
          this.showMessage.error = true;
          this.showMessage.message = resp ? resp.body.message : '';
        }
      }, error => {
        this.showMessage.error = true;
        this.showMessage.success = false;
        this.showMessage.message = error ? error.error.message : '';
      });
    }
  }
  updateData(data: any) {
    this.userService.updateUser(data).subscribe((result: HttpResponse<any> | HttpErrorResponse | any ) => {
      const resp = result;
      const status = resp.status;
      if (status !== null && status === 200) {
        this.showMessage.success = true;
        this.onUserCreationSuccess.emit(this.showMessage.success);
        this.showMessage.error = false;
        this.showMessage.message = resp ? resp.body.message : '';
      } else {
        this.showMessage.success = false;
        this.showMessage.error = true;
        this.showMessage.message = resp ? resp.body.message : '';
      }
    }, error => {
      this.showMessage.error = true;
      this.showMessage.success = false;
      this.showMessage.message = error ? error.error.message : '';
    });
  }

  resetForms() {
   this.parentForm.reset();
    this.appAddress.resetForm();
    this.ref.close();
  }
  loadParentForm() {
    this.parentForm = this.formBuilder.group({
      id: [null],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      middleNames: [''],
      occupation: [''],
      phoneNumber: ['', Validators.required],
      emailAddress: [null],
      gender: ['', Validators.required],
      picture: [''],
      userType: ['parent'],
      address: [null, Validators.required],
      studentIDs: [''],
      studentFullName: [''],
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
      userType: 'parent',
    };
  }
  onParentAddressChange(addressWrapper: AddressWrapper ) {
    if (!addressWrapper.isValid) {
      this.parentForm.controls['address'].setErrors({ invalidAddress: true });
    } else {
      this.parentForm.controls['address'].setErrors(null);
      this.parentForm.patchValue({address: addressWrapper.address});
    }
  }
  pickUser(event: any, student: Student) {
    event.preventDefault();
    this.listDisplay = 'none';
    let studentFound = false;
    if (this.selectedStudents.length === 0) {
      this.selectedStudents.push(student);
    } else {
      this.selectedStudents.forEach(p => {
          if (p.id === student.id) {
           studentFound = true;
          }
      });
      if (!studentFound) {
        this.selectedStudents.push(student);
      }
    }
    this.parentForm.patchValue({
      selectedStudent: this.studentIds,
      studentFullName: '',
    });
  }
  search(value: string) {
    const param = this.getParam();
    param.userType = 'student';
    param.search = value;
    this.userService.search(param).subscribe((result) => {
      this.students = [];
      if (result && result.success) {
        this.students = result.data;
        this.listDisplay = 'block';
      }
    });
  }
  onStudentFullInputChanges() {
    this.parentForm.get('studentFullName').valueChanges.subscribe(value => {
      if (value) {
        this.search(value);
      }
    });
  }

  selectedParentChanged(event: any, student: Student) {
    if (!event) {
      for (let i = 0; i < this.selectedStudents.length; i++) {
        if (this.selectedStudents[i].id === student.id) {
          this.selectedStudents.splice(i, 1);
        }
     }
    } else {
      this.selectedStudents.push(student);
    }
  }
}
