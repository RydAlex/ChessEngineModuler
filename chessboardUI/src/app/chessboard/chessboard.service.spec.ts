import { TestBed, inject } from '@angular/core/testing';

import { ChessboardService } from './chessboard.service';

describe('ChessboardService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ChessboardService]
    });
  });

  it('should be created', inject([ChessboardService], (service: ChessboardService) => {
    expect(service).toBeTruthy();
  }));
});
