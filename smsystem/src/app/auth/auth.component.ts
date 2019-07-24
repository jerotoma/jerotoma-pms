import { Component, OnDestroy, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { NbAuthComponent, NbAuthService } from '@nebular/auth';

import { AuthService } from '../../app/services/auth'


@Component({
  selector: 'ngx-auth',
  styleUrls: ['./auth.component.scss'],
  templateUrl: './auth.component.html',
})
export class AuthComponent extends NbAuthComponent implements OnDestroy, OnInit {
    // showcase of how to use the onAuthenticationChange method
  constructor(
     protected auth: NbAuthService, protected location: Location) {
        super(auth, location);
  }
  ngOnInit() {

  }
}
