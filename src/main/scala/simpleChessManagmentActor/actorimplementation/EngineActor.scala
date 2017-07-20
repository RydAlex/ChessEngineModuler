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
      log.info(engineName + " receive timeoutClass request :" + timeoutClass.id )
      val engineNames = engineRunner.getEngineNames
      if(engineNames.contains(engineName)){
        log.info(engineName + " was send with params : chessboard " + timeoutClass.chessboardFen +  " timeout: " + timeoutClass.duration)
        sender ! MessageBack(engineName, engineRunner.RunEngineWithGoTimeoutCommand(engineName,timeoutClass.chessboardFen, timeoutClass.duration))
      }
    case depthClass: DepthMessage =>
      log.info(engineName + " receive depthClass request :" + depthClass.id )
      val engineNames = engineRunner.getEngineNames
      if(engineNames.contains(engineName)){
        log.info(engineName + " was send with params : chessboard " + depthClass.chessboardFen +  " depth: " + depthClass.depth)
        sender ! MessageBack(engineName, engineRunner.RunEngineWithGoDepthCommand(engineName, depthClass.chessboardFen, depthClass.depth))
      }
  }
}
