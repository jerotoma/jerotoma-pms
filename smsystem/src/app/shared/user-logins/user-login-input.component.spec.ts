import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserLoginInputComponent } from './user-login-input.component';

describe('PasswordComponent', () => {
  let component: UserLoginInputComponent;
  let fixture: ComponentFixture<UserLoginInputComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserLoginInputComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserLoginInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
