import { TestBed, inject } from '@angular/core/testing';

import { RuleBoxService } from './rule-box.service';

describe('RuleBoxService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RuleBoxService]
    });
  });

  it('should be created', inject([RuleBoxService], (service: RuleBoxService) => {
    expect(service).toBeTruthy();
  }));
});
