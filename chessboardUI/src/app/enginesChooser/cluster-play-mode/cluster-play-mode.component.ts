import {Component, EventEmitter, Input, OnChanges, Output} from '@angular/core';
import { ClusterPlayModeService } from './cluster-play-mode.service';
import {EloEngineValue} from '../elo-engine-value';

@Component({
  selector: 'app-cluster-play-mode',
  templateUrl: './cluster-play-mode.component.html',
  styleUrls: ['./cluster-play-mode.component.css'],
  providers: [ ClusterPlayModeService ]
})
export class ClusterPlayModeComponent implements OnChanges {

  @Input() enginesNames: string[];
  @Input() ruleOfPlay: string;
  @Output() chosenType: EventEmitter<string>;

  nameElo: EloEngineValue[] = [];
  chosenClusterGameType: string;
  loading = false;

  constructor(private clusterPlayModeService: ClusterPlayModeService) {
    this.chosenType = new EventEmitter<string>();

    let element = new EloEngineValue();
    element.name = 'RANDOM';
    this.nameElo.push(element);
    element = new EloEngineValue();
    element.name = 'ELO_SIMPLE';
    this.nameElo.push(element);
    element = new EloEngineValue();
    element.name = 'ELO_VOTE_WITH_DISTRIBUTION';
    this.nameElo.push(element);
    element = new EloEngineValue();
    element.name = 'ELO_VOTE_WITH_ELO';
    this.nameElo.push(element);
  }

  ngOnChanges() {
    this.chosenClusterGameType = '';
    if (this.enginesNames.length == 4){
      this.findEloOfClusterRule();
    }
  }

  typeChange(gameType: string){
    if (gameType != ''){
      console.log('EMIT SEND');
      this.chosenType.emit(gameType);
    }
  }

  private findEloOfClusterRule() {
    console.log('initMet');
    const data =  {
      'names' : this.enginesNames,
      'rule'  : this.ruleOfPlay
    };
    this.loading = true;

    this.clusterPlayModeService.getClusterMode(data).subscribe(
      data => {
        for (const nameElem of this.nameElo){
          if (data[nameElem.name] != undefined){
            nameElem.elo = data[nameElem.name];
          }
        }
        this.loading = false;
      },
      err => {
        console.log(err);
      }
    );
  }
}
