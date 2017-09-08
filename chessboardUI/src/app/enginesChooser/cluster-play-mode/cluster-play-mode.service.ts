import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

@Injectable()
export class ClusterPlayModeService {

  constructor(private http: Http) { }

  getClusterMode(data) {
    return this.http.post('http://localhost:8080/approach/best', data)
      .map(res => res.json());
  }
}
