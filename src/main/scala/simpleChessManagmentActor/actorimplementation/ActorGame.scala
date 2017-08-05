package simpleChessManagmentActor.actorimplementation

import akka.actor._
import akka.event.Logging
import chess.amqp.message.{EngineEloPair, SingleMoveResult, TypeOfMessageExtraction}
import chess.engine.processor.core.enginemechanism.FenGenerator

import scala.collection.mutable.ListBuffer
import scala.util.Random


/**
  * Created by aleksanderr on 09/04/17.
  */
class ActorGame(system: ActorSystem, name: Seq[String], elo: Seq[EngineEloPair]) extends Actor {


  val log = Logging(context.system, this)

  var timeOutGameOrAnother: Boolean = true
  var isSingleMove: Boolean = false
  var valOfGameRule: Int = 0
  var halfMoveCounter = 0
  var chessboard: String = ""
  var senderRef: ActorRef = null
  var listLength: Int = 0
  var whoWin : Int = 0
  var activeListOne = true
  var answers :ListBuffer[MessageBack] = ListBuffer()
  var enginesOne :List[ActorRef] = List[ActorRef]()
  var enginesTwo :List[ActorRef] = List[ActorRef]()
  var typeOfGame: TypeOfMessageExtraction = TypeOfMessageExtraction.RANDOM
  val oneMoveMessagesParser = new OneMoveMessagesParser()

  def receive = {
    case message: MessageBack =>
      log.info(name + "msg from engine received from " + message.engineName +  " no :" + message.id + " with answer: " + message.message)
      answers += message
      if(answers.length == listLength/2) {
        self ! AssumingMessage()
      }

    case assumingMsg: AssumingMessage =>
      logMessageOnEnterance(assumingMsg)
      val answer :MessageBack = extractMoveWhichShouldBeTakenInThisGame()
      val singleMoveResults : List[SingleMoveResult] = oneMoveMessagesParser.parseResultsToMoveResults(answers)
      val isCheckmate = new FenGenerator(chessboard).isMoveACheckmate(answer.message)
      log.info(answer.message + " is checkmate: " + isCheckmate)
      if(isCheckmate) {
        log.info(name + " - " + answer.engineName + " LOST!")
        if(activeListOne){
          self ! EndGame(2) // Second Players Win Game
        } else {
          self ! EndGame(1) // First Player Win Game -
        }
      }
      else {
        try{
          if(UpdateChessboardOrTellIsItDraw(answer)){
            self ! EndGame(0) // Draw was detected
          }
          else if(isSingleMove){
            self ! SingleMoves(singleMoveResults) // If single move than just return those moves
          }
          else {
            tellForOtherHalfOfEnginesToStartCounting() // if no condition was detected just start counting at second side
          }
        } catch{
          case ex: Exception => {
            log.info("Engine takes move which does not exist")
            self ! EndGame(-1)
          }
        }
      }


    case depthMessage: DepthMessage =>
      //log.info(name + " Depth msg received and will be send to engines:" + depthMessage.id)
      if(activeListOne){
        for (engine <- enginesOne){
          engine ! depthMessage
        }
      } else {
        for (engine <- enginesTwo){
          engine ! depthMessage
        }
      }

    case timeOutMessage: TimeOutMessage =>
      //log.info(name + " Timeout msg received :" + timeOutMessage.id)
      if(activeListOne){
        for (engine <- enginesOne){
          engine ! TimeOutMessage(timeOutMessage.chessboardFen, timeOutMessage.duration)
        }
      } else {
        for (engine <- enginesTwo){
          engine ! TimeOutMessage(timeOutMessage.chessboardFen, timeOutMessage.duration)
        }
      }

    case singleMoves: SingleMoves =>
      for (engine <- enginesOne){
        engine ! PoisonPill
      }
      for (engine <- enginesTwo){
        engine ! PoisonPill
      }
      senderRef ! singleMoves


    case endGame: EndGame =>
      //log.info(name + " Lets kill everything... no:")
      for (engine <- enginesOne){
        engine ! PoisonPill
      }
      for (engine <- enginesTwo){
        engine ! PoisonPill
      }
      senderRef ! endGame

     case initGame: InitGame =>
       //log.info(name + " InitGame Command received :")
       this.typeOfGame = initGame.typeOfGame
       this.isSingleMove = initGame.isSingleMove
       this.senderRef = sender
       listLength = name.length
        if(name.length % 2 != 0 ){
          throw new RuntimeException("game do not have same number of engines on both sides")
        }
        val engineLength = name.length/2
        for(i <- Range(0, engineLength)){
          self ! CreateNewActorInFirstGroup(name(i).trim)
        }
        for(i <- Range(engineLength, name.length)){
          self ! CreateNewActorInSecondGroup(name(i).trim)
        }

    case timeoutRule :StartNewGameWithTimeoutRule =>
      chessboard = new FenGenerator(timeoutRule.chessboardFen).returnFenStringPositions()
      timeOutGameOrAnother = true
      valOfGameRule = timeoutRule.timeout
      log.info(name + " game with timeout rule should be started shortly :" + timeoutRule.id)
      while(enginesOne.length + enginesTwo.length != listLength){
      }
      self ! new TimeOutMessage(chessboard,timeoutRule.timeout)

    case depthRule :StartNewGameWithDepthRule =>
      chessboard = new FenGenerator(depthRule.chessboardFen).returnFenStringPositions()
      timeOutGameOrAnother = false
      valOfGameRule = depthRule.depth
      log.info(name + " game with depth rule should be started shortly :" + depthRule.id)
      while(enginesOne.length + enginesTwo.length != listLength){}
      self ! new DepthMessage(chessboard,depthRule.depth)

    case newActor: CreateNewActorInFirstGroup =>
      //log.info(name + " - " + newActor.engineName + " actor is created in first group - msg no :" + newActor.id)
      enginesOne = system.actorOf(Props(new EngineActor(newActor.engineName))) :: enginesOne

    case newActor: CreateNewActorInSecondGroup =>
      //log.info(name + " - " + newActor.engineName + " actor is created in second group - msg no :" + newActor.id)
      enginesTwo = system.actorOf(Props(new EngineActor(newActor.engineName))) :: enginesTwo
  }


  def UpdateChessboardOrTellIsItDraw(answer: MessageBack): Boolean= {
    val fen: FenGenerator = new FenGenerator(chessboard)
    val halfMoveShouldBroke = fen.insertMove(answer.message)
    if(halfMoveShouldBroke){
      halfMoveCounter = 0
    } else {
      halfMoveCounter += 1
    }
    if(halfMoveCounter >= 50){
      log.info(name + " THERE WAS A DRAW!!!!!!") // Example: "7K/7P/5q2/8/4k3/8/8/8 w - -"
      return true
    }
    chessboard = fen.returnFenStringPositions()
    log.info(s"$name - move: ${answer.message} ,chessboard: $chessboard, halfmove: $halfMoveCounter")
    false
  }

  def logMessageOnEnterance(assumingMsg: AssumingMessage) = {
    //log.info(name + " Enough message receive - lets assume :" + assumingMsg.id)
    log.info(s" All answers $answers")
  }

  def extractMoveWhichShouldBeTakenInThisGame(): MessageBack = {
    var answer :MessageBack = null
    val messageExtractionMethods = new MessageExtractionMethods(name,answers,elo)
    if (typeOfGame.equals(TypeOfMessageExtraction.RANDOM)) {
      answer = messageExtractionMethods.extractMessageInARandomApproach()
    } else if (typeOfGame.equals(TypeOfMessageExtraction.ELO_SIMPLE)) {
      answer = messageExtractionMethods.extractMessageInAEloSimpleApproach()
    } else if (typeOfGame.equals(TypeOfMessageExtraction.ELO_VOTE_WITH_ELO)) {
      answer = messageExtractionMethods.extractMessageInAEloVotingApproach()
    } else if (typeOfGame.equals(TypeOfMessageExtraction.ELO_VOTE_WITH_DISTRIBUTION)) {
      answer = messageExtractionMethods.extractMessageInAEloDistributionApproach()
    }
    answer
  }

  def tellForOtherHalfOfEnginesToStartCounting() = {
    answers.clear()
    activeListOne = !activeListOne
    if (timeOutGameOrAnother) {
      self ! TimeOutMessage(chessboard, valOfGameRule)
    } else {
      self ! DepthMessage(chessboard, valOfGameRule)
    }
  }
}
