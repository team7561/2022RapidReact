import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShooterPopupComponent } from './shooter-popup.component';

describe('ShooterPopupComponent', () => {
  let component: ShooterPopupComponent;
  let fixture: ComponentFixture<ShooterPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShooterPopupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShooterPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
