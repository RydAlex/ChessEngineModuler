import { TestBed, inject } from '@angular/core/testing';

import { ClusterPlayModeService } from './cluster-play-mode.service';

describe('ClusterPlayModeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ClusterPlayModeService]
    });
  });

  it('should be created', inject([ClusterPlayModeService], (service: ClusterPlayModeService) => {
    expect(service).toBeTruthy();
  }));
});
