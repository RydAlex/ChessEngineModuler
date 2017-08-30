import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { NavComponent } from './nav/nav.component';
import { ChessboardComponent } from './chessboard/chessboard.component';
import { EngineBoxComponent } from './enginesChooser/engine-box/engine-box.component';
import { RuleBoxComponent } from './enginesChooser/rule-box/rule-box.component';
import { ClusterPlayModeComponent } from './enginesChooser/cluster-play-mode/cluster-play-mode.component';
import { SpinnerModule } from 'angular2-spinner/dist';

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    ChessboardComponent,
    EngineBoxComponent,
    RuleBoxComponent,
    ClusterPlayModeComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    SpinnerModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
