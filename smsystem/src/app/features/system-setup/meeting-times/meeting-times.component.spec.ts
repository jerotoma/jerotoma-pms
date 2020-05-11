import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeetingTimesComponent } from './meeting-times.component';

describe('MeetingTimesComponent', () => {
  let component: MeetingTimesComponent;
  let fixture: ComponentFixture<MeetingTimesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeetingTimesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeetingTimesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
