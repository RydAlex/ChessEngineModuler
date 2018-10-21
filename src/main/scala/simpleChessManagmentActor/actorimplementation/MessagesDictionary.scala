package simpleChessManagmentActor.actorimplementation

import chess.database.entities.Engine
import chess.engine.processor.core.enginemechanism.FenGenerator

/**
  * Created by aleksanderr on 09/04/17.
  */


case class MessageBack(var engineName: String,var message :String, override val id: String = MessagesDictionary.uuid()) extends IdMessage
case class AssumingMessage(override val id: String = MessagesDictionary.uuid()) extends IdMessage
case class EndGame(whoWin: Integer, fenMovesInGame: String, override val id: String = MessagesDictionary.uuid()) extends IdMessage
case class InitGame(chessEngineListOne: Seq[Engine], chessEngineListTwo: Seq[Engine], roundsPerEngine: Int, override val id: String = MessagesDictionary.uuid()) extends IdMessage
case class StartNewGameWithTimeoutRule(timeout: Int, chessboardFen: String = new FenGenerator().returnFenStringPositions(), override val id: String = MessagesDictionary.uuid()) extends IdMessage
case class CreateNewActorInFirstGroup(engineName :String, override val id: String = MessagesDictionary.uuid()) extends IdMessage
case class CreateNewActorInSecondGroup(engineName :String, override val id: String = MessagesDictionary.uuid()) extends IdMessage
case class TimeOutMessage(chessboardFen: String, duration: Int, override val id: String = MessagesDictionary.uuid()) extends IdMessage



object MessagesDictionary {
  def uuid() = java.util.UUID.randomUUID.toString
}

trait IdMessage{
  val id: String
}