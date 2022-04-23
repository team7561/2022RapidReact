import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SwerveDirectionComponent } from './swerve-direction.component';

describe('SwerveDirectionComponent', () => {
  let component: SwerveDirectionComponent;
  let fixture: ComponentFixture<SwerveDirectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SwerveDirectionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SwerveDirectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
