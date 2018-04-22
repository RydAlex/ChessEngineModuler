package simpleChessManagmentActor

import akka.actor.ActorRef
import chess.amqp.message.{ChessJSONObject, EngineEloPair, SingleMoveResult, TypeOfMessageExtraction}
import simpleChessManagmentActor.actorimplementation.EndGame
import scala.collection.JavaConverters._
import scala.collection.JavaConversions.mapAsScalaMap

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
    val chessEloList: Seq[EngineEloPair] = JavaConverters.asScalaBuffer(chessObject.getChessGamesEloValue)
    val sizeOfFirstGroup: Int = chessObject.getSizeOfEnginesInFirstGroup
    val sizeOfSecondGroup : Int = chessObject.takeSizeOfEnginesInSecondGroup


    val gameShaper = new GameShaper()
    val actor: ActorRef = gameShaper.defineNewGameWithThoseEngine(typeOfGame, isSingleMove ,chessEngineList, chessEloList,
                                                                                        sizeOfFirstGroup, sizeOfSecondGroup)

    gameShaper.startGameWithDepthRule(actor, depth, chessboard) match {
      case gameResult : EndGame  =>  {
          chessObject.setAnswer(gameResult.whoWin.toString)
          chessObject.setFenMovesInGame(gameResult.fenMovesInGame)
          chessObject.setEngineNamesVotesMap(JavaConverters.bufferAsJavaList(gameResult.decisionMadeInThisGame))
      }
      //case move       : List[SingleMoveResult]  =>    chessObject.setSingleMoveResults(JavaConverters.seqAsJavaList(move))
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
    val chessEloList: Seq[EngineEloPair] = JavaConverters.asScalaBuffer(chessObject.getChessGamesEloValue)
    val sizeOfFirstGroup: Int = chessObject.getSizeOfEnginesInFirstGroup
    val sizeOfSecondGroup: Int = chessObject.takeSizeOfEnginesInSecondGroup

    val gameShaper = new GameShaper()
    val actor: ActorRef = gameShaper.defineNewGameWithThoseEngine(typeOfGame, isSingleMove, chessEngineList, chessEloList,
                                                                                          sizeOfFirstGroup, sizeOfSecondGroup)

    gameShaper.startGameWithTimeOutRule(actor, timeout, chessboard) match {
      case gameResult : EndGame  =>  {
        chessObject.setAnswer(gameResult.whoWin.toString)
        chessObject.setFenMovesInGame(gameResult.fenMovesInGame)
        chessObject.setEngineNamesVotesMap(JavaConverters.bufferAsJavaList(gameResult.decisionMadeInThisGame))
      }
      case singleMove : String  =>    chessObject.setAnswer(singleMove)
    }
    chessObject
  }
}
