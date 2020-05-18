import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeetingTimesDeleteComponent } from './meeting-times-delete.component';

describe('MeetingTimesDeleteComponent', () => {
  let component: MeetingTimesDeleteComponent;
  let fixture: ComponentFixture<MeetingTimesDeleteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeetingTimesDeleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeetingTimesDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
