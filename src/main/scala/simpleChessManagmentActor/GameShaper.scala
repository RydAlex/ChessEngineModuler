package simpleChessManagmentActor

import akka.actor.ActorRef
import com.typesafe.scalalogging.Logger
import engineprocessor.core.enginemechanism.FenGenerator
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import simpleChessManagmentActor.actorimplementation.ActorRunner
import simpleChessManagmentActor.possiblegames.OneVersusOneGame

import scala.collection.immutable.Range


/**
  * Created by aleksanderr on 02/04/17.
  */

object GameShaper extends App {

  val logger = Logger(LoggerFactory.getLogger(GameShaper.getClass))
  val actorRunner = new ActorRunner("engine")
  var one = new OneVersusOneGame
  one.game("gull", "toga2")
  var one2 = new OneVersusOneGame
  one.game("stockfish", "glaurung")
  System.exit(0)
}
