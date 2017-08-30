import { Component } from '@angular/core';
import { EloEngineValue } from './enginesChooser/elo-engine-value';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  enginesValues: EloEngineValue[];
  enginesNames: string[];
  clusterName: string;
  displayEloRule: string;
  displayClusterGameType: string;


  onTableElementChange(element: EloEngineValue[]){
    this.displayClusterGameType = '';
    this.enginesValues = element;
  }

  onEngineNameChoose(elements: string[]) {
    this.enginesNames = elements;
    this.clusterName = '';
    this.displayClusterGameType = '';
    for (const engineName of this.enginesNames){
      if (this.clusterName == ''){
        this.clusterName = engineName;
      } else {
        this.clusterName += '_' + engineName;
      }
    }
  }

  ruleChoose(eloRule: string) {
    this.displayEloRule = eloRule;
  }

  onClusterPlayType(clusterPlayType: any) {
    console.log(clusterPlayType);
    this.displayClusterGameType = clusterPlayType;
  }

  canBeEnabled() {
    return this.enginesNames != undefined && (this.enginesNames.length > 0);
  }

  isChessboardEnabled() {
    if (this.displayClusterGameType != null && this.displayClusterGameType != ''){
      if (this.displayEloRule != null && this.displayEloRule != ''){
        if (this.clusterName != null && this.clusterName != ''){
          return true;
        }
      }
    }
    return false;
  }
}
