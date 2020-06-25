import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MediaShowComponent } from './media-show.component';

describe('MediaShowComponent', () => {
  let component: MediaShowComponent;
  let fixture: ComponentFixture<MediaShowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MediaShowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MediaShowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
