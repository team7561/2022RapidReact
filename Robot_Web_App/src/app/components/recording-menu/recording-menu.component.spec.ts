import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecordingMenuComponent } from './recording-menu.component';

describe('RecordingMenuComponent', () => {
  let component: RecordingMenuComponent;
  let fixture: ComponentFixture<RecordingMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecordingMenuComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecordingMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
