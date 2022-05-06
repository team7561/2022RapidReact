import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RobotPosComponent } from './robot-pos.component';

describe('RobotPosComponent', () => {
  let component: RobotPosComponent;
  let fixture: ComponentFixture<RobotPosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RobotPosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RobotPosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
