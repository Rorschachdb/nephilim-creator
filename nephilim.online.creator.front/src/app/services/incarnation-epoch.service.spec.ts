import {TestBed} from '@angular/core/testing';

import {IncarnationEpochService} from './incarnation-epoch.service';

describe('IncarnationEpochService', () => {
  let service: IncarnationEpochService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IncarnationEpochService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });


});
