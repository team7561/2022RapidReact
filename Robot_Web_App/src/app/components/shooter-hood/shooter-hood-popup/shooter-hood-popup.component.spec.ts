import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShooterHoodPopupComponent } from './shooter-hood-popup.component';

describe('ShooterHoodPopupComponent', () => {
  let component: ShooterHoodPopupComponent;
  let fixture: ComponentFixture<ShooterHoodPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShooterHoodPopupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShooterHoodPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
