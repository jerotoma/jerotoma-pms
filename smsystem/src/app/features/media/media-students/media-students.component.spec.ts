import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MediaStudentsComponent } from './media-students.component';

describe('MediaStudentsComponent', () => {
  let component: MediaStudentsComponent;
  let fixture: ComponentFixture<MediaStudentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MediaStudentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MediaStudentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
