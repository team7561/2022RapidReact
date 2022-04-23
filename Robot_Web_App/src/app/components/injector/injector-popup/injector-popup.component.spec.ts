import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InjectorPopupComponent } from './injector-popup.component';

describe('InjectorPopupComponent', () => {
  let component: InjectorPopupComponent;
  let fixture: ComponentFixture<InjectorPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InjectorPopupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InjectorPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
