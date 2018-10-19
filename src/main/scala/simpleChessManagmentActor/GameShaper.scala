package simpleChessManagmentActor

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.scalalogging.Logger
import chess.engine.processor.core.enginemechanism.FenGenerator
import simpleChessManagmentActor.actorimplementation._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps
import scala.util.{Failure, Success}
/**
  * Created by aleksanderr on 02/04/17.
  */

class GameShaper{

  implicit var timeout: Timeout = Timeout(4 hours)
  val logger = Logger("GameShaper")
  val system = ActorSystem("System")
  var f : Future[Any]= Future(0)

  def defineNewGameWithThoseEngine(): ActorRef = {
    val actorGame = system.actorOf(Props(new ActorGame(system)))
    f = actorGame ? InitGame()
    actorGame
  }

  def startGameWithTimeOutRule(actor: ActorRef, timeout: Int , fenChessboard: String = new FenGenerator().returnFenStringPositions()): Any ={
    Thread.sleep(3000)
    actor ! StartNewGameWithTimeoutRule(timeout, fenChessboard)

    val enginesAnswer = Await.result(f, Duration.Inf) match {
      case endGame: EndGame => endGame
      case singleMove: SingleMoves => singleMove.singleMoveResult
      case Failure(fail) => logger.info("Failure in Await.result at GameShaper")
    }
    enginesAnswer
  }

}