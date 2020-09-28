import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassesAutoGenerateComponent } from './classes-auto-generate.component';

describe('ClassesAutoGenerateComponent', () => {
  let component: ClassesAutoGenerateComponent;
  let fixture: ComponentFixture<ClassesAutoGenerateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClassesAutoGenerateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClassesAutoGenerateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
