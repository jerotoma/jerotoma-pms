import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeetingTimesCreateComponent } from './meeting-times-create.component';

describe('MeetingTimesCreateComponent', () => {
  let component: MeetingTimesCreateComponent;
  let fixture: ComponentFixture<MeetingTimesCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeetingTimesCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeetingTimesCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
