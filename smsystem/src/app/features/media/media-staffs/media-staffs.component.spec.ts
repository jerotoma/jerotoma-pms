import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MediaStaffsComponent } from './media-staffs.component';

describe('MediaStaffsComponent', () => {
  let component: MediaStaffsComponent;
  let fixture: ComponentFixture<MediaStaffsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MediaStaffsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MediaStaffsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
