package simpleChessManagmentActor.actorimplementation

import akka.actor._
import akka.event.Logging
import akka.stream.Supervision
import chess.engine.processor.core.enginemechanism.FenGenerator

import scala.collection.mutable.ListBuffer


/**
  * Created by aleksanderr on 09/04/17.
  */
class ActorGame(system: ActorSystem) extends Actor {


  val log = Logging(context.system, this)

  var timeOutGameOrAnother: Boolean = true
  var isSingleMove: Boolean = false
  var valOfGameRule: Int = 0
  var halfMoveCounter = 0
  var chessboard: String = ""
  var senderRef: ActorRef = _
  var whoWin : Int = 0
  var activeListOne = true
  var answers :ListBuffer[MessageBack] = ListBuffer()
  var enginesOne :List[ActorRef] = List[ActorRef]()
  var enginesTwo :List[ActorRef] = List[ActorRef]()
  var enginesOneName :List[String] = List[String]()
  var enginesTwoName :List[String] = List[String]()
  var movesInGameFenVersion: String =""

  def receive = {
    case message: MessageBack => receiveMessageAction(message);
    case assumingMsg: AssumingMessage => assumeAllMessages(assumingMsg)
    case timeOutMessage: TimeOutMessage => reactOnTimeOutMessage(timeOutMessage)
    case singleMoves: SingleMoves => singleMoveEnding(singleMoves)
    case endGame: EndGame => endGameBehaviour(endGame)
    case initGame: InitGame => initNewGame(initGame)
    case timeoutRule :StartNewGameWithTimeoutRule => startNewGameWithTimeoutRule(timeoutRule)
    case newActor: CreateNewActorInFirstGroup => createActorInFirstGroup(newActor)
    case newActor: CreateNewActorInSecondGroup => createActorInSecondGroup(newActor)
  }


  private def startNewGameWithTimeoutRule(timeoutRule: StartNewGameWithTimeoutRule): Unit = {
    chessboard = new FenGenerator(timeoutRule.chessboardFen).returnFenStringPositions()
    timeOutGameOrAnother = true
    valOfGameRule = timeoutRule.timeout
//    log.info(name + " game with timeout rule should be started shortly :" + timeoutRule.id)
    self ! new TimeOutMessage(chessboard, timeoutRule.timeout)
  }

  private def receiveMessageAction(message: MessageBack): Unit = {
//    log.info(name + "msg from engine received from " + message.engineName +  " no :" + message.id + " with answer: " + message.message)
    answers += message
    if(activeListOne){
//      if(answers.length == clusterOneSize) {
//        self ! AssumingMessage()
//      }
    } else {
//      if(answers.length == clusterTwoSize) {
//        self ! AssumingMessage()
//      }
    }
  }

  private def assumeAllMessages(assumingMsg: AssumingMessage): Unit = {
    logMessageOnEnterance(assumingMsg)
    val answer :MessageBack = extractMoveWhichShouldBeTakenInThisGame()
    println(answer)
    val isCheckmate = new FenGenerator(chessboard).isMoveACheckmate(answer.message)
    log.info(answer.message + " is checkmate: " + isCheckmate)
    if(isCheckmate) {
//      log.info(name + " - " + answer.engineName + " LOST!")
      if(activeListOne){
//        self ! EndGame(2, decisionMadeInThisGame, movesInGameFenVersion) // Second Players Win Game
      } else {
//        self ! EndGame(1, decisionMadeInThisGame, movesInGameFenVersion) // First Player Win Game
      }
    }
    else {
      try{
        movesInGameFenVersion += answer.message+" "
        if(UpdateChessboardOrTellIsItDraw(answer)){
//          self ! EndGame(0, decisionMadeInThisGame, movesInGameFenVersion) // Draw was detected
        }
        else if(isSingleMove){
          self ! SingleMoves(chessboard) // If single move than just return those moves
        }
        else {
          tellForOtherHalfOfEnginesToStartCounting() // if no condition was detected just start counting at second side
        }
      } catch{
        case ex: Exception => {
          log.info("Engine takes move which does not exist")
//          self ! EndGame(-1, decisionMadeInThisGame, movesInGameFenVersion)
        }
      }
    }
  }

  private def createActorInSecondGroup(newActor: CreateNewActorInSecondGroup): Unit = {
    //log.info(name + " - " + newActor.engineName + " actor is created in second group - msg no :" + newActor.id)
    enginesTwo = system.actorOf(Props(new EngineActor(newActor.engineName))) :: enginesTwo
  }

  private def createActorInFirstGroup(newActor: CreateNewActorInFirstGroup): Unit = {
    //log.info(name + " - " + newActor.engineName + " actor is created in first group - msg no :" + newActor.id)
    enginesOne = system.actorOf(Props(new EngineActor(newActor.engineName))) :: enginesOne
  }

  private def initNewGame(initGame: InitGame): Unit = {
    //log.info(name + " InitGame Command received :")
    this.senderRef = sender
//    listLength = name.length
//    for (i <- Range(0, clusterOneSize)) {
//      enginesOneName = name(i).trim :: enginesOneName
//      self ! CreateNewActorInFirstGroup(name(i).trim)
//    }
//    for (i <- Range(clusterOneSize, name.length)) {
//      enginesTwoName = name(i).trim :: enginesTwoName
//      self ! CreateNewActorInSecondGroup(name(i).trim)
//    }
  }

  private def singleMoveEnding(singleMoves: SingleMoves): Unit = {
    for (engine <- enginesOne) {
      engine ! PoisonPill
    }
    for (engine <- enginesTwo) {
      engine ! PoisonPill
    }
    senderRef ! singleMoves
  }

  private def endGameBehaviour(endGame: EndGame): Unit = {
    //log.info(name + " Lets kill everything... no:")
    Supervision.resume
    for (engine <- enginesOne) {
      engine ! PoisonPill
    }
    for (engine <- enginesTwo) {
      engine ! PoisonPill
    }
    senderRef ! endGame
  }

  private def reactOnTimeOutMessage(timeOutMessage: TimeOutMessage): Unit = {
    //log.info(name + " Timeout msg received :" + timeOutMessage.id)
    if (activeListOne) {
      for (engine <- enginesOne) {
        engine ! TimeOutMessage(timeOutMessage.chessboardFen, timeOutMessage.duration)
      }
    } else {
      for (engine <- enginesTwo) {
        engine ! TimeOutMessage(timeOutMessage.chessboardFen, timeOutMessage.duration)
      }
    }
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
      //log.info(name + " THERE WAS A DRAW!!!!!!") // Example: "7K/7P/5q2/8/4k3/8/8/8 w - -"
      return true
    }
    chessboard = fen.returnFenStringPositions()
    //log.info(s"$name - move: ${answer.message} ,chessboard: $chessboard, halfmove: $halfMoveCounter")
    false
  }

  def logMessageOnEnterance(assumingMsg: AssumingMessage): Unit = {
    //log.info(name + " Enough message receive - lets assume :" + assumingMsg.id)
    log.info(s" All answers $answers")
  }

  def extractMoveWhichShouldBeTakenInThisGame(): MessageBack = {
    var answer :MessageBack = null
    var messageExtractionMethods: MessageExtractionMethods = null
    var fenGenerator : FenGenerator = new FenGenerator()
    var filteredAnswers :ListBuffer[MessageBack] = ListBuffer()
    var answersInRightOrder :ListBuffer[MessageBack] = ListBuffer()
    if (activeListOne) {
      for(engine <- enginesOneName) {
        for(answer <- answers) {
          if(engine.equals(answer.engineName)){
            answersInRightOrder += answer
          }
        }
      }
    } else {
      for(engine <- enginesTwoName) {
        for(answer <- answers) {
          if(engine.equals(answer.engineName)){
            answersInRightOrder += answer
          }
        }
      }
    }
    for(answer <- answersInRightOrder){
      if(fenGenerator.isMoveACheckmate(answer.message)){
        log.info(answer + " was removed cause was a checkmate")
      } else if(new FenGenerator(chessboard).isMoveExistForActivePlayer(answer.message)) {
        filteredAnswers += answer
      } else {
        log.info(answer + " was removed cause was not exist for that player")
      }
    }

    def methodTest: Unit = {
      if (filteredAnswers.nonEmpty) {
        messageExtractionMethods = new MessageExtractionMethods(filteredAnswers)
        answer = messageExtractionMethods.extractMessageInARandomApproach()
      }
      if (answer == null) {
        answer = MessageBack(answers.head.engineName, "a1a1") // default move which means lost the game - couldnt be null cause it was broke forward game
      }
    }

    methodTest
    answer
  }

  def tellForOtherHalfOfEnginesToStartCounting(): Unit = {
    answers.clear()
    activeListOne = !activeListOne
    if (timeOutGameOrAnother) {
      self ! TimeOutMessage(chessboard, valOfGameRule)
    } else {
      self ! DepthMessage(chessboard, valOfGameRule)
    }
  }
}
