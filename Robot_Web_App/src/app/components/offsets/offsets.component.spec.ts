import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OffsetsComponent } from './offsets.component';

describe('OffsetsComponent', () => {
  let component: OffsetsComponent;
  let fixture: ComponentFixture<OffsetsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OffsetsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OffsetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
