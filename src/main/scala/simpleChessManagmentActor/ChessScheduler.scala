package simpleChessManagmentActor

import AMQPManagment.utils.TypeOfMessageExtraction
import AMQPManagment.utils.data.{ChessJSONObject, SingleMoveResult}
import akka.actor.ActorRef

import scala.collection.JavaConverters

/**
  * Created by aleksanderr on 15/04/17.
  */
object ChessScheduler {

  // DEPTH GAME
  def startGameWithDepthRule(chessObject: ChessJSONObject) : ChessJSONObject = {
    val depth: Int = chessObject.getDepth
    val chessboard: String = chessObject.getFen
    val typeOfGame : TypeOfMessageExtraction = chessObject.getTypeOfGame
    val isSingleMove : Boolean = chessObject.getIsSingleMove
    val chessEngineList: Seq[String] = JavaConverters.asScalaBuffer(chessObject.getChessGameName)

    val actor: ActorRef = GameShaper.defineNewGameWithThoseEngine(typeOfGame, isSingleMove ,chessEngineList)
    GameShaper.startGameWithDepthRule(actor, depth, chessboard) match {
      case gameResult : String => {
        chessObject.setAnswer(gameResult)
      }
      case move : List[SingleMoveResult] => {
        chessObject.setSingleMoveResults(JavaConverters.seqAsJavaList(move))
      }
    }
    chessObject
  }

  //TIMEOUT GAME
  def startGameWithTimeoutRule(chessObject: ChessJSONObject): ChessJSONObject ={
    val timeout: Int = chessObject.getTimeout
    val chessboard: String = chessObject.getFen
    val typeOfGame : TypeOfMessageExtraction = chessObject.getTypeOfGame
    val isSingleMove : Boolean = chessObject.getIsSingleMove
    val chessEngineList: Seq[String] = JavaConverters.asScalaBuffer(chessObject.getChessGameName)

    val actor: ActorRef = GameShaper.defineNewGameWithThoseEngine(typeOfGame, isSingleMove, chessEngineList)
    GameShaper.startGameWithTimeOutRule(actor, timeout, chessboard) match {
      case gameResult : String => {
        chessObject.setAnswer(gameResult)
      }
      case move : List[SingleMoveResult] => {
        chessObject.setSingleMoveResults(JavaConverters.seqAsJavaList(move))
      }
    }
    chessObject
  }
}
