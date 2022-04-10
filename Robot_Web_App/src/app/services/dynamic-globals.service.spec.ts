import { TestBed } from '@angular/core/testing';

import { DynamicGlobalsService } from './dynamic-globals.service';

describe('DynamicGlobalsService', () => {
  let service: DynamicGlobalsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DynamicGlobalsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
