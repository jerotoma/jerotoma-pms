import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkDaysDeleteComponent } from './work-days-delete.component';

describe('WorkDaysDeleteComponent', () => {
  let component: WorkDaysDeleteComponent;
  let fixture: ComponentFixture<WorkDaysDeleteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkDaysDeleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkDaysDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
