import {Component, EventEmitter, Output} from '@angular/core';
import { RuleBoxService } from "./rule-box.service";
import {EloEngineValue} from "../elo-engine-value";

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

  @Output() engineEloTableChange: EventEmitter<EloEngineValue[]>;

  constructor(private ruleBoxService: RuleBoxService) {
    this.engineEloTableChange = new EventEmitter<EloEngineValue[]>();
  }

  ruleChange(event){
    this.ruleDesc = event.split("_")[0];
    this.ruleValue = event.split("_")[1];
    var eloEngineTable: EloEngineValue[] = [];
    this.ruleBoxService.getEnginesElo(this.ruleDesc, this.ruleValue).subscribe(
      data => {
        for(let engineDataIndex in data){
          var element = data[engineDataIndex];
          var eloEngine: EloEngineValue = new EloEngineValue();
          eloEngine.name = element['engineName'];
          eloEngine.elo = element['eloValue'];
          eloEngineTable.push(eloEngine);
        }
        this.engineEloTableChange.emit(eloEngineTable);
      }
    );
  }
}
