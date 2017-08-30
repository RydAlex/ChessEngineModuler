import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ChessboardService} from './chessboard.service';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/interval';

declare var chessboardManager: any;

@Component({
  selector: 'app-chessboard',
  templateUrl: './chessboard.component.html',
  styleUrls: ['./chessboard.component.css'],
  providers: [ ChessboardService ]
})
export class ChessboardComponent implements OnInit {

  @ViewChild('fen') fen;
  @Input() gameEngines: string[];
  @Input() rule: string;
  @Input() type: string;

  chessboardManager: any;
  oldChessboard: any;

  constructor(private chessboardService: ChessboardService) {}

  ngOnInit(): void {
    console.log("gameEngines");
    console.log(this.gameEngines);
    console.log("rule");
    console.log(this.rule);
    console.log("type");
    console.log(this.type);
    this.chessboardManager = (new chessboardManager());
    this.oldChessboard = this.chessboardManager.getFenPositions().trim();

    Observable.interval(500).subscribe(_ => {
      const actualChessFen = this.chessboardManager.getFenPositions().trim();
      if (this.oldChessboard != actualChessFen){
        this.oldChessboard = actualChessFen;
        this.chessboardService.sendMoveRequestToSeveralEngines(
          this.createObjectToMoveRequest(actualChessFen)).subscribe(
          data => {
            console.log("data from serv:");
            this.oldChessboard = data.answer;
            this.chessboardManager.setFenPositions(data.answer);
            this.oldChessboard = this.chessboardManager.getFenPositions().trim();
            console.log(data.answer);
          },
          err => {
            console.log(err);
          }
        );
      }
    });
  }

  createObjectToMoveRequest(chessboardFen){
    return {
      "gameEngines" : this.gameEngines,
      "rule" : this.rule,
      "type" : this.type,
      "chessboardFen" : chessboardFen
    }
  }
}
