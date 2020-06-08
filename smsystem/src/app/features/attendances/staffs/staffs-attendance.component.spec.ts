import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StaffsAttendanceComponent } from './staffs-attendance.component';

describe('StaffsComponent', () => {
  let component: StaffsAttendanceComponent;
  let fixture: ComponentFixture<StaffsAttendanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StaffsAttendanceComponent ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StaffsAttendanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
