package simpleChessManagmentActor.actorimplementation

import akka.actor.Actor
import engineprocessor.interfaces.{EngineRunner, EngineRunnerImpl}

import scala.language.postfixOps

/**
  * Created by aleksanderr on 18/03/17.
  */
class EngineActor(engineName: String, val engineRunner: EngineRunner = new EngineRunnerImpl) extends Actor {

  def receive = {
    case timeoutClass: TimeOutMessage =>
      val engineNames = engineRunner.getEngineNames
      if(engineNames.contains(engineName)){
        sender() ! engineRunner.RunEngineWithGoTimeoutCommand(engineName,timeoutClass.chessboardFen, timeoutClass.duration)
      }
    case depthClass: DepthMessage =>
      val engineNames = engineRunner.getEngineNames
      if(engineNames.contains(engineName)){
        sender() ! engineRunner.RunEngineWithGoDepthCommand(engineName, depthClass.chessboardFen, depthClass.depth)
      }
  }
}
