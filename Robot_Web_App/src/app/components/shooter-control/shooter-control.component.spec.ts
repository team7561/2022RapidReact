import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShooterControlComponent } from './shooter-control.component';

describe('ShooterControlComponent', () => {
  let component: ShooterControlComponent;
  let fixture: ComponentFixture<ShooterControlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShooterControlComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShooterControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
