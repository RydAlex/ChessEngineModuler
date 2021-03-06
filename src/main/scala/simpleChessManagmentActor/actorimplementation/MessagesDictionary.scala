package simpleChessManagmentActor.actorimplementation

import chess.amqp.message.{SingleMoveResult, TypeOfMessageExtraction, GameVotingStats}
import chess.engine.processor.core.enginemechanism.FenGenerator

import scala.collection.mutable.ListBuffer

/**
  * Created by aleksanderr on 09/04/17.
  */


case class MessageBack(var engineName: String,var message :String, override val id: String = MessagesDictionary.uuid()) extends IdMessage
case class AssumingMessage(override val id: String = MessagesDictionary.uuid()) extends IdMessage
case class EndGame(whoWin: Integer, decisionMadeInThisGame :ListBuffer[GameVotingStats], override val id: String = MessagesDictionary.uuid()) extends IdMessage
case class SingleMoves(singleMoveResult: String, override val id: String = MessagesDictionary.uuid()) extends IdMessage
case class InitGame(typeOfGame: TypeOfMessageExtraction, isSingleMove: Boolean, override val id: String = MessagesDictionary.uuid()) extends IdMessage
case class StartNewGameWithTimeoutRule(timeout: Int, chessboardFen: String = new FenGenerator().returnFenStringPositions(), override val id: String = MessagesDictionary.uuid()) extends IdMessage
case class StartNewGameWithDepthRule(depth: Int, chessboardFen: String = new FenGenerator().returnFenStringPositions(), override val id: String = MessagesDictionary.uuid()) extends IdMessage
case class CreateNewActorInFirstGroup(engineName :String, override val id: String = MessagesDictionary.uuid()) extends IdMessage
case class CreateNewActorInSecondGroup(engineName :String, override val id: String = MessagesDictionary.uuid()) extends IdMessage
case class SetTypeOfDecisionInGame(depthOrTimeout: Boolean, override val id: String = MessagesDictionary.uuid()) extends IdMessage
case class DepthMessage(chessboardFen: String, depth: Int, override val id: String = MessagesDictionary.uuid()) extends IdMessage
case class TimeOutMessage(chessboardFen: String, duration: Int, override val id: String = MessagesDictionary.uuid()) extends IdMessage



object MessagesDictionary {
  def uuid() = java.util.UUID.randomUUID.toString
}

trait IdMessage{
  val id: String
}