import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrivetrainPopupComponent } from './drivetrain-popup.component';

describe('DrivetrainPopupComponent', () => {
  let component: DrivetrainPopupComponent;
  let fixture: ComponentFixture<DrivetrainPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DrivetrainPopupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DrivetrainPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
