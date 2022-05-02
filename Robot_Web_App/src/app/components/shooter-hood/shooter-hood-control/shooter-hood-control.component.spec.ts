import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShooterHoodControlComponent } from './shooter-hood-control.component';

describe('ShooterHoodControlComponent', () => {
  let component: ShooterHoodControlComponent;
  let fixture: ComponentFixture<ShooterHoodControlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShooterHoodControlComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShooterHoodControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
