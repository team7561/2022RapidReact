import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IntakeControlComponent } from './intake-control.component';

describe('IntakeControlComponent', () => {
  let component: IntakeControlComponent;
  let fixture: ComponentFixture<IntakeControlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IntakeControlComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IntakeControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
