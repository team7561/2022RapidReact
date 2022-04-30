import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BallGraphComponent } from './ball-graph.component';

describe('BallGraphComponent', () => {
  let component: BallGraphComponent;
  let fixture: ComponentFixture<BallGraphComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BallGraphComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BallGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
