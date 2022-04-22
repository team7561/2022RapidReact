import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisionGraphComponent } from './vision-graph.component';

describe('VisionGraphComponent', () => {
  let component: VisionGraphComponent;
  let fixture: ComponentFixture<VisionGraphComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VisionGraphComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VisionGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
