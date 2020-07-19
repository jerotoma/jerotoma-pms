import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import { Role, User } from 'app/models';
import {
  RoleService,
  UserService,
  ModalService,
} from 'app/services';
import { QueryParam, USER_TYPE } from 'app/utils';

@Component({
  selector: 'app-role-assignation',
  templateUrl: `./role-assignation.component.html`,
  styleUrls: [`./role-assignation.component.scss`],
})
export class RoleAssignationComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = 'assignation';
  @Output() onCreationSuccess = new EventEmitter();
  @Input() role: Role;

  roles: Role[] = null;
  userType: string = USER_TYPE.TEACHER;
  userTypes: string[] = Object.values(USER_TYPE);
  roleAssignationForm: FormGroup;
  roleIDs: number[] = [];
  listDisplay: string = 'none';
  isSubmitting: boolean = false;
  isLoading: boolean = false;

  constructor(
    private roleService:  RoleService,
    private userService: UserService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    protected ref: NbDialogRef<RoleAssignationComponent>) {}

  ngOnInit() {
    this.userTypes = this.userTypes.filter(userType => userType !== USER_TYPE.ALL);
    this.loadForm();
    if (!this.role) {
      this.loadRoles();
    } else {
      this.roles = [];
      this.roles.push(this.role);
    }
  }
  patchRole() {
    this.roleAssignationForm.patchValue({
      id: this.role.id,
    });
  }
  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.isSubmitting = true;
    this.roleService.assignationRole(this.roleAssignationForm.value)
    .subscribe((success: boolean ) => {
      this.isSubmitting = false;
        if (success) {
          this.modalService.openSnackBar('Role Assignation has been successifully done', 'success');
          this.dismiss();
        }
    }, error => {
      this.isSubmitting = false;
    });
  }

  userSelected(user: User) {
   if (user) {
      this.roleAssignationForm.patchValue({
        userId: user.userId,
      });
    }
  }

  loadForm() {
    this.roleAssignationForm = this.formBuilder.group({
      userType: [USER_TYPE.TEACHER, Validators.required ],
      userId: ['', Validators.required ],
      roleIDs: ['', Validators.required ],
    });

    this.roleAssignationForm.get('userType').valueChanges.subscribe(userType => {
      this.userType = userType;
    });
  }

  checkedChange(checked: boolean, role: Role) {
    if (checked) {
      this.roleIDs.push(role.id);
    } else {
      for (let i = 0; i < this.roleIDs.length; i++) {
        if ( this.roleIDs[i] === role.id) {
          this.roleIDs.splice(i, 1);
        }
     }
    }
    this.roleAssignationForm.patchValue({
      roleIDs: this.roleIDs,
    });
  }

  loadRoles() {
    this.roles = [];
    this.isLoading = true;
    this.roleService.loadRoles()
      .subscribe((roles: Role[]) => {
        this.isLoading = false;
        if (roles) {
          this.roles = roles;
        }
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
      userType: 'role',
    };
  }
}
