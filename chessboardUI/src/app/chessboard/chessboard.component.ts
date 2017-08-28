import { Component, OnInit } from '@angular/core';

declare var chessboardManager: any;

@Component({
  selector: 'app-chessboard',
  templateUrl: './chessboard.component.html',
  styleUrls: ['./chessboard.component.css']
})
export class ChessboardComponent implements OnInit {

  ngOnInit(): void {
    chessboardManager();
  }

  constructor() {
  }
}
