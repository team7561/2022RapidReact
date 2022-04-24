import { TestBed } from '@angular/core/testing';

import { RecordLoadDataService } from './record-load-data.service';

describe('RecordLoadDataService', () => {
  let service: RecordLoadDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RecordLoadDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
