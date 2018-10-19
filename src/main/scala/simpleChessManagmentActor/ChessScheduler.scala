package simpleChessManagmentActor

import akka.actor.ActorRef
import chess.amqp.message.{ChessJSONObject}
import simpleChessManagmentActor.actorimplementation.EndGame

import scala.collection.JavaConverters

/**
  * Created by aleksanderr on 15/04/17.
  */
object ChessScheduler {


  //TIMEOUT GAME
  def startGameWithTimeoutRule(chessObject: ChessJSONObject): ChessJSONObject ={
    val timeout: Int = chessObject.getTimeout
    val chessboard: String = chessObject.getFen

    val gameShaper = new GameShaper()
    val actor: ActorRef = gameShaper.defineNewGameWithThoseEngine()

    gameShaper.startGameWithTimeOutRule(actor, timeout, chessboard) match {
      case gameResult : EndGame  =>  {
        chessObject.setAnswer(gameResult.whoWin.toString)
      }
    }
    chessObject
  }
}
