import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelScreenComponent } from './panel-screen.component';

describe('PanelScreenComponent', () => {
  let component: PanelScreenComponent;
  let fixture: ComponentFixture<PanelScreenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PanelScreenComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PanelScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
