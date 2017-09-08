import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import 'rxjs/add/operator/retry';
import 'rxjs/add/operator/map';

@Injectable()
export class RuleBoxService {

  constructor(private http: Http) { }

  getEnginesElo(ruleDesc, ruleValue) {
    return this.http.get('http://localhost:8080/engine/names/' + ruleDesc  + '/' + ruleValue)
      .map(res => res.json());
  }

}
