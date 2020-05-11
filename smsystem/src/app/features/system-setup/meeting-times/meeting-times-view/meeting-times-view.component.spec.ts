import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MeetingTimesViewComponent } from './meeting-times-view.component';

describe('MeetingTimesViewComponent', () => {
  let component: MeetingTimesViewComponent;
  let fixture: ComponentFixture<MeetingTimesViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MeetingTimesViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeetingTimesViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
