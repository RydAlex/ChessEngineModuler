package simpleChessManagmentActor.actorimplementation

import chess.amqp.message.SingleMoveResult
import com.typesafe.scalalogging.Logger

import scala.collection.mutable.ListBuffer

/**
  * Created by aleksanderr on 02/07/17.
  */
class OneMoveMessagesParser {

  val log = Logger("OneMoveMessagesParser")

  def parsingMethod(message: MessageBack): SingleMoveResult = {
    System.out.println(message)
    val moveResult = new SingleMoveResult()
    moveResult.setEngineName(message.engineName)
    moveResult.setMoveResult(message.message)
    moveResult
  }

    def parseResultsToMoveResults(answers: ListBuffer[MessageBack]): List[SingleMoveResult] = {
      val elements :ListBuffer[SingleMoveResult] = ListBuffer[SingleMoveResult]()
      answers.foreach{
        message => elements += parsingMethod(message)
      }
      log.info("MOVERESULTS: " + elements.toString())
      elements.toList
    }
}
