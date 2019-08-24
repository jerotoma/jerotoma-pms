import { Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from 'app/services/auth/auth.service';
import { ShowMessage } from 'app/models/messages/show-message.model';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  showMessage: ShowMessage = {
    error: false,
    success: false,
    message: '',
  };
  submitted = true;
  errors: string[] = [];
  messages: string[] = [];
  user: any = {};
  constructor(
    protected service: AuthService,
    protected router: Router) {}

  ngOnInit() {
  }

  register(): void {
    this.errors = [];
    this.messages = [];
    this.submitted = true;
    this.service.register(this.user).subscribe((result: any) => {
      this.submitted = false;
    });
  }

  getConfigValue(key: string) {

  }

}
