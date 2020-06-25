import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MediaTeachersComponent } from './media-teachers.component';

describe('MediaTeachersComponent', () => {
  let component: MediaTeachersComponent;
  let fixture: ComponentFixture<MediaTeachersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MediaTeachersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MediaTeachersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
