import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TeachersAttendanceComponent } from './teachers-attendance.component';

describe('TeachersComponent', () => {
  let component: TeachersAttendanceComponent;
  let fixture: ComponentFixture<TeachersAttendanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TeachersAttendanceComponent ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TeachersAttendanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
