package simpleChessManagmentActor

import akka.actor.ActorRef
import engineprocessor.core.enginemechanism.{EngineAvailabilityScanner, FenGenerator}
import simpleChessManagmentActor.actorimplementation.TypeOfGame

import scala.util.Random

/**
  * Created by aleksanderr on 15/04/17.
  */
object ChessScheduler extends App {

  var listOfGameToPlay = new GameMultipleEngineSetCreator().getMultipleEnginesForGame(2)
  startGameWithDepthRule(3, listOfGameToPlay)


  // DEPTH GAME
  def startGameWithDepthRule(depth: Int ,chessEngineList: Seq[String]): Unit ={
    val actor: ActorRef = GameShaper.defineNewGameWithThoseEngine(TypeOfGame.RANDOM,chessEngineList)
    GameShaper.startGameWithDepthRule(actor, depth)
  }

  def startGameWithDepthRule(chessboard: String, depth: Int, chessEngineList: Seq[String]): Unit ={
    val actor: ActorRef = GameShaper.defineNewGameWithThoseEngine(TypeOfGame.RANDOM,chessEngineList)
    GameShaper.startGameWithDepthRule(actor, depth, chessboard)
  }

  //TIMEOUT GAME
  def startGameWithTimeoutRule(timeout: Int ,chessEngineList: Seq[String]): Unit ={
    val actor: ActorRef = GameShaper.defineNewGameWithThoseEngine(TypeOfGame.RANDOM,chessEngineList)
    GameShaper.startGameWithTimeOutRule(actor, timeout)
  }

  def startGameWithTimeoutRule(chessboard: String, timeout: Int, chessEngineList: Seq[String]): Unit ={
    val actor: ActorRef = GameShaper.defineNewGameWithThoseEngine(TypeOfGame.RANDOM,chessEngineList)
    GameShaper.startGameWithTimeOutRule(actor, timeout, chessboard)
  }
}
