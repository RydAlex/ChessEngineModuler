package simpleChessManagmentActor.actorimplementation

import akka.actor.Actor
import akka.event.Logging
import chess.engine.processor.interfaces.{EngineRunner, EngineRunnerImpl}

import scala.language.postfixOps

/**
  * Created by aleksanderr on 18/03/17.
  */
class EngineActor(engineName: String, val engineRunner: EngineRunner = new EngineRunnerImpl) extends Actor {

  val log = Logging(context.system, this)

  def receive = {
    case timeoutClass: TimeOutMessage =>
      log.info(engineName + " receive timeout request")
      val engineNames = engineRunner.getEngineNames
      if(engineNames.contains(engineName)){
        sender ! MessageBack(engineName, engineRunner.RunEngineWithGoTimeoutCommand(engineName,timeoutClass.chessboardFen, timeoutClass.duration))
      }
  }
}
