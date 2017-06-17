package simpleChessManagmentActor

import akka.actor.ActorRef
import engineprocessor.core.enginemechanism.{EngineAvailabilityScanner, FenGenerator}
import simpleChessManagmentActor.actorimplementation.TypeOfGame

import scala.util.Random

/**
  * Created by aleksanderr on 15/04/17.
  */
object ChessScheduler {
  //var listOfGameToPlay = new GameMultipleEngineSetCreator().getMultipleEnginesForGame(8)
  //startGameWithDepthRule(3, listOfGameToPlay)

  // DEPTH GAME
  def startGameWithDepthRule(depth: Int ,chessEngineList: Seq[String]): Integer ={
    val actor: ActorRef = GameShaper.defineNewGameWithThoseEngine(TypeOfGame.RANDOM,chessEngineList)
    GameShaper.startGameWithDepthRule(actor, depth)
    new Integer(1)
  }

  def startGameWithDepthRule(chessboard: String, depth: Int, chessEngineList: Seq[String]): Integer ={
    val actor: ActorRef = GameShaper.defineNewGameWithThoseEngine(TypeOfGame.RANDOM,chessEngineList)
    GameShaper.startGameWithDepthRule(actor, depth, chessboard)
    new Integer(1)
  }

  //TIMEOUT GAME
  def startGameWithTimeoutRule(timeout: Int ,chessEngineList: Seq[String]): Integer ={
    val actor: ActorRef = GameShaper.defineNewGameWithThoseEngine(TypeOfGame.RANDOM,chessEngineList)
    GameShaper.startGameWithTimeOutRule(actor, timeout)
    new Integer(1)
  }

  def startGameWithTimeoutRule(chessboard: String, timeout: Int, chessEngineList: Seq[String]): Integer ={
    val actor: ActorRef = GameShaper.defineNewGameWithThoseEngine(TypeOfGame.RANDOM,chessEngineList)
    GameShaper.startGameWithTimeOutRule(actor, timeout, chessboard)
    new Integer(1)
  }
}
