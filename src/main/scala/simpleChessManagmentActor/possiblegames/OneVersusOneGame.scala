package simpleChessManagmentActor.possiblegames

import akka.actor.ActorRef
import com.typesafe.scalalogging.Logger
import engineprocessor.core.enginemechanism.FenGenerator
import simpleChessManagmentActor.actorimplementation.ActorRunner

/**
  * Created by aleksanderr on 02/04/17.
  */
class OneVersusOneGame extends GameVersus{

  val logger = Logger("simpleChessManagmentActor.possiblegames.OneVersusOneGame")
  val actorRunner = new ActorRunner("engine")

  def game(engineName: String*): Unit = {
    var actualFen = new FenGenerator().returnFenStringPositions()

    val fenGenerator: FenGenerator = new FenGenerator()

    val actor: ActorRef = actorRunner.createChessActorInSystem(engineName(0))
    val actor2: ActorRef = actorRunner.createChessActorInSystem(engineName(1))
    while(true){
      var value = actorRunner.countDepth(actualFen, 3, actor,actor,actor,actor)

      if(fenGenerator.isMoveACheckmate(value)){
        logger.info(engineName(0) + " won the game!!!!")
        return
      }
      actualFen = new FenGenerator(actualFen).insertMove(value).returnFenStringPositions()
      logger.info("FEN of " + engineName(0) + ": " + actualFen)

      value = actorRunner.countDepth(actualFen, 3, actor2)
      if(fenGenerator.isMoveACheckmate(value)){
        logger.info(engineName(1) + " won the game!!!!")
        return
      }
      actualFen = new FenGenerator(actualFen).insertMove(value).returnFenStringPositions()
      logger.info("FEN of " + engineName(1) + " " + actualFen)
    }
  }
}
