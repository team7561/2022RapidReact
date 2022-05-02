import { TestBed } from '@angular/core/testing';

import { GetRobotDataService } from './get-robot-data.service';

describe('GetRobotDataService', () => {
  let service: GetRobotDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetRobotDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
