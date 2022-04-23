import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InjectorControlComponent } from './injector-control.component';

describe('InjectorControlComponent', () => {
  let component: InjectorControlComponent;
  let fixture: ComponentFixture<InjectorControlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InjectorControlComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InjectorControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
