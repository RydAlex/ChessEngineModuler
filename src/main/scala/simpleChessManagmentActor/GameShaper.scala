package simpleChessManagmentActor

import akka.actor.{ActorRef, ActorSystem, Inbox, Props}
import simpleChessManagmentActor.actorimplementation._
import akka.pattern.ask
import akka.util.Timeout
import engineprocessor.core.enginemechanism.FenGenerator

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future, Promise}
import scala.language.postfixOps
import scala.util.{Failure, Success}
/**
  * Created by aleksanderr on 02/04/17.
  */

object GameShaper{

  implicit var timeout: Timeout = Timeout(30 minutes)

  val system = ActorSystem("System")

  var f : Future[Any]= Future(0)
  //checkmate game -  "3k1Q2/8/3K4/8/8/8/8/8 b - -"
  //TODO: handle failure

  def defineNewGameWithThoseEngine(typeOfGame: TypeOfGame.gameMetodology, chessEngineListForGame: Seq[String]): ActorRef = {
    val actorGame = system.actorOf(Props(new ActorGame(system, chessEngineListForGame)))
    f = actorGame ? InitGame(typeOfGame)
    actorGame
  }

  def startGameWithDepthRule(actor: ActorRef, depth: Int , fenChessboard: String = new FenGenerator().returnFenStringPositions()): Unit ={
    Thread.sleep(3000)
    actor ! StartNewGameWithDepthRule(depth, fenChessboard)
    f onComplete {
      case Success(value) => println("success")
      case Failure(ex) => println("fail")
    }
    Await.ready(f, Duration.Inf)
  }

  def startGameWithTimeOutRule(actor: ActorRef, timeout: Int , fenChessboard: String = new FenGenerator().returnFenStringPositions()): Unit ={
    Thread.sleep(3000)
    actor ! StartNewGameWithTimeoutRule(timeout, fenChessboard)
    f onComplete {
      case Success(value) => println("success")
      case Failure(ex) => println("fail")
    }
    Await.ready(f, Duration.Inf)
  }

}