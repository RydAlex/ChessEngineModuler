package simpleChessManagmentActor

import akka.actor.ActorRef
import chess.amqp.message.ChessJSONObject
import chess.database.entities.Engine
import simpleChessManagmentActor.actorimplementation.EndGame

import scala.collection.JavaConverters

/**
  * Created by aleksanderr on 15/04/17.
  */
object ChessScheduler {

  def startGameWithTimeoutRule(chessObject: ChessJSONObject): ChessJSONObject ={
    val timeout: Int = chessObject.getTimeout
    val chessboard: String = chessObject.getFen
    val chessEngineListOne: Seq[Engine] = JavaConverters.asScalaBuffer(chessObject.getClusterBattle.getChessClusterOne.getEngineList)
    val chessEngineListTwo: Seq[Engine] = JavaConverters.asScalaBuffer(chessObject.getClusterBattle.getChessClusterTwo.getEngineList)

    val gameShaper = new GameShaper()
    val actor: ActorRef = gameShaper.defineNewGameWithThoseEngine(chessEngineListOne, chessEngineListTwo)

    gameShaper.startGameWithTimeOutRule(actor, timeout, chessboard) match {
      case gameResult : EndGame  =>  {
        chessObject.setAnswer(gameResult.whoWin.toString)
      }
    }
    chessObject
  }
}
