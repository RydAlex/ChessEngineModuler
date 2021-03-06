import {Component, EventEmitter, Output} from '@angular/core';
import { RuleBoxService } from './rule-box.service';
import {EloEngineValue} from '../elo-engine-value';

@Component({
  selector: 'app-rule-box',
  templateUrl: './rule-box.component.html',
  styleUrls: ['./rule-box.component.css'],
  providers: [ RuleBoxService ]
})
export class RuleBoxComponent {
  rule;
  ruleDesc;
  ruleValue;
  loading = false;

  @Output() engineEloTableChange: EventEmitter<EloEngineValue[]>;
  @Output() engineEloRule: EventEmitter<string>;

  constructor(private ruleBoxService: RuleBoxService) {
    this.engineEloTableChange = new EventEmitter<EloEngineValue[]>();
    this.engineEloRule = new EventEmitter<string>();
  }

  ruleChange(event){
    const eloEngineTable: EloEngineValue[] = [];
    this.engineEloRule.emit(event);
    this.ruleDesc = event.split('_')[0];
    this.ruleValue = event.split('_')[1];
    this.loading = true;
    this.ruleBoxService.getEnginesElo(this.ruleDesc, this.ruleValue).subscribe(
      data => {
        for (const element of data){
          const eloEngine: EloEngineValue = new EloEngineValue();
          eloEngine.name = element['engineName'];
          eloEngine.elo = element['eloValue'];
          eloEngineTable.push(eloEngine);
          this.loading = false;
        }
        this.engineEloTableChange.emit(eloEngineTable);
      },
      err => {
        this.loading = false;
      }
    );
  }
}
