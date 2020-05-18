import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkDaysCreateComponent } from './work-days-create.component';

describe('WorkDaysCreateComponent', () => {
  let component: WorkDaysCreateComponent;
  let fixture: ComponentFixture<WorkDaysCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkDaysCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkDaysCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
