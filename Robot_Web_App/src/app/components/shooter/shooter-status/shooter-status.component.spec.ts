import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShooterStatusComponent } from './shooter-status.component';

describe('ShooterStatusComponent', () => {
  let component: ShooterStatusComponent;
  let fixture: ComponentFixture<ShooterStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShooterStatusComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShooterStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
