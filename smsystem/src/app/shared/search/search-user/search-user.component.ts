import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { User } from 'app/models';
import { QueryParam, USER_TYPE } from 'app/utils';

import { UserService } from 'app/services';

@Component({
  selector: 'app-search-user',
  templateUrl: './search-user.component.html',
  styleUrls: ['./search-user.component.scss'],
})
export class SearchUserComponent implements OnInit {

  @Input('userType') userType: string =  USER_TYPE.teacher;
  @Input('user') user: User  = null;
  @Input('title') title: string = 'Login Cridentials';
  @Output() onUserSelected: EventEmitter<User> = new EventEmitter();

  userForm: FormGroup;
  listDisplay: string = 'none';

  users: User[] = [];

  constructor(
    private userService:  UserService,
    private formBuilder: FormBuilder,
    ) {}

  ngOnInit() {
    this.loadForm();
    this.onSearchInputChanges();
    this.onChanges();
  }

  loadForm() {
    this.userForm = this.formBuilder.group({
      searchKey: ['', Validators.required],
    });
  }
  onSearchInputChanges() {
    this.userForm.get('searchKey').valueChanges.subscribe(value => {
      if (value) {
        this.search(value);
      }
    });
  }

  onChanges() {
    if (this.user) {
      this.userForm.patchValue({
        searchKey: this.user.fullName,
      });
    }
  }

  pickUser(event: any, user: User) {
    event.preventDefault();
    if (user) {
      this.user = user;
      this.listDisplay = 'none';
      this.onUserSelected.emit(this.user);
      this.userForm.patchValue({
         searchKey: user.fullName,
      });
    }

  }

  onSubmit() {


  }

  getParam(): QueryParam {
    return {
      page: 1,
      pageSize: 10,
      orderby: 'DESC',
      status: '',
      search: '',
      fieldName: '',
      userType: this.userType,
    };
  }

  search(value: string) {
    const param = this.getParam();
    param.search = value;
    this.userService.search(param).subscribe((users: User[]) => {
      this.users = [];
      if (users) {
        this.users = users;
        this.listDisplay = 'block';
      }
    });
  }

}
