import { Injectable } from '@angular/core';
import {Http} from "@angular/http";

@Injectable()
export class ChessboardService {

  constructor(private http: Http) { }

  sendMoveRequestToSeveralEngines(data) {
    return this.http.post('http://localhost:8080/play', data)
      .map(res => res.json());
  }
}
