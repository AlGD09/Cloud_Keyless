import { TestBed } from '@angular/core/testing';

import { RentedWallboxService } from './rentedWallbox.service';

describe('WallboxService', () => {
  let service: RentedWallboxService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RentedWallboxService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
