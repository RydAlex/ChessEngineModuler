import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import { EloEngineValue } from '../elo-engine-value';

@Component({
  selector: 'app-engine-box',
  templateUrl: './engine-box.component.html',
  styleUrls: ['./engine-box.component.css'],
})
export class EngineBoxComponent implements OnInit, OnChanges {
  @Input() enginesValues: EloEngineValue[] = [];
  @Output() chosenEnginesNames:  EventEmitter<string[]>;
  selectedName = '';
  engineNames: EloEngineValue[];
  engineNamesUsed: string[] = [];
  isEnabled = true;

  constructor() {
    this.chosenEnginesNames = new EventEmitter<string[]>();
  }

  ngOnChanges() {
    this.engineNames = this.enginesValues;
  }

  ngOnInit() {
    this.engineNames = this.enginesValues;
  }

  onSelect(engineName: string): void {
    if (this.engineNamesUsed.indexOf(engineName) < 0) {
      if (this.isEnabled){
        this.engineNamesUsed.push(engineName);
        if (this.engineNamesUsed.length >= 4 ){
          this.chosenEnginesNames.emit(this.engineNamesUsed);
          this.isEnabled = false;
        }
      }
    } else {
      this.engineNamesUsed.splice(this.engineNamesUsed.indexOf(engineName), 1);
      this.chosenEnginesNames.emit([]);
      this.isEnabled = true;
    }
    this.constructName();
  }

  constructName(){
    this.selectedName = '';
    for (const name of this.engineNamesUsed){
      if (this.selectedName == ''){
        this.selectedName = name;
      } else {
        this.selectedName = this.selectedName + '_' + name;
      }
    }
  }

  checkIfDisabled(engineName: string) {
    if (this.isEnabled){
      return false;
    } else {
      return !(this.engineNamesUsed.indexOf(engineName) >= 0);
    }
  }

  isChecked(name: string) {
    return !(this.engineNamesUsed.indexOf(name) < 0);
  }
}
