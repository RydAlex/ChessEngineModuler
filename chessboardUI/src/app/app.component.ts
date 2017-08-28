import { Component } from '@angular/core';
import {EloEngineValue} from "./enginesChooser/elo-engine-value";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  enginesValues: EloEngineValue[];

  onTableElementChange(element: EloEngineValue[]){
    this.enginesValues = element;
  }
}
