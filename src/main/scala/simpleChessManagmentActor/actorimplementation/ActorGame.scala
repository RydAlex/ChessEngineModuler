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

  var valOfGameRule: Int = 0
  var halfMoveCounter = 0
  var roundCounter = 0
  var chessboard: String = ""
  var senderRef: ActorRef = _
  var whoWin : Int = 0
  var roundsPerEngine = 5
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
    case endGame: EndGame => endGameBehaviour(endGame)
    case initGame: InitGame => initNewGame(initGame)
    case timeoutRule :StartNewGameWithTimeoutRule => startNewGameWithTimeoutRule(timeoutRule)
    case newActor: CreateNewActorInFirstGroup => createActorInFirstGroup(newActor)
    case newActor: CreateNewActorInSecondGroup => createActorInSecondGroup(newActor)
  }

  private def describeFirstCluster(): Unit = {
    var summary: String = "First Cluster contain:"
    for(engineIndex <- enginesOneName.indices){
      summary += " " + engineIndex + ": " + enginesOneName(engineIndex)
    }
    log.info(summary)
  }

  private def describeSecondCluster(): Unit = {
    var summary: String = "Second Cluster contain:"
    for(engineIndex <- enginesTwoName.indices){
      summary += " " + engineIndex + ": " + enginesTwoName(engineIndex)
    }
    log.info(summary)
  }


  private def startNewGameWithTimeoutRule(timeoutRule: StartNewGameWithTimeoutRule): Unit = {
    chessboard = new FenGenerator(timeoutRule.chessboardFen).returnFenStringPositions()
    valOfGameRule = timeoutRule.timeout
    roundCounter = 0
    self ! TimeOutMessage(chessboard, timeoutRule.timeout)
  }

  private def receiveMessageAction(message: MessageBack): Unit = {
    answers += message
    if(activeListOne){
      self ! AssumingMessage()
    } else {
      roundCounter += 1
      self ! AssumingMessage()
    }
  }

  private def assumeAllMessages(assumingMsg: AssumingMessage): Unit = {
    val answer :MessageBack = extractMoveWhichShouldBeTakenInThisGame()
    val isCheckmate = new FenGenerator(chessboard).isMoveACheckmate(answer.message)
    log.info(answer.message + " is checkmate: " + isCheckmate)
    if(isCheckmate) {
      log.info(answer.engineName + " LOST!")
      if(activeListOne){
        self ! EndGame(2, movesInGameFenVersion) // Second Players Win Game
      } else {
        self ! EndGame(1, movesInGameFenVersion) // First Player Win Game
      }
    }
    else {
      try{
        movesInGameFenVersion += answer.message+" "
        if(UpdateChessboardOrTellIsItDraw(answer)){
          self ! EndGame(0, movesInGameFenVersion) // Draw was detected
        }
        else {
          tellForOtherHalfOfEnginesToStartCounting() // if no condition was detected just start counting at second side
        }
      } catch{
        case ex: Exception =>
          log.info("Engine takes move which does not exist")
          self ! EndGame(-1, movesInGameFenVersion)
      }
    }
  }

  private def createActorInSecondGroup(newActor: CreateNewActorInSecondGroup): Unit = {
    enginesTwo = system.actorOf(Props(new EngineActor(newActor.engineName))) :: enginesTwo
  }

  private def createActorInFirstGroup(newActor: CreateNewActorInFirstGroup): Unit = {
    enginesOne = system.actorOf(Props(new EngineActor(newActor.engineName))) :: enginesOne
  }

  private def initNewGame(initGame: InitGame): Unit = {
    log.info("InitGame Command received :")
    this.roundsPerEngine = initGame.roundsPerEngine
    this.senderRef = sender
    for (i <- initGame.chessEngineListOne.indices) {
      enginesOneName = initGame.chessEngineListOne(i).getEngineName.trim :: enginesOneName
      self ! CreateNewActorInFirstGroup(initGame.chessEngineListOne(i).getEngineName.trim)
    }
    for (i <- initGame.chessEngineListTwo.indices) {
      enginesTwoName = initGame.chessEngineListTwo(i).getEngineName.trim :: enginesTwoName
      self ! CreateNewActorInSecondGroup(initGame.chessEngineListTwo(i).getEngineName.trim)
    }
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
    var whichEngine = Math.floor(roundCounter / roundsPerEngine).toInt
    if(whichEngine >= enginesOne.length){
      whichEngine = enginesOne.length - 1
    }
    describeFirstCluster()
    describeSecondCluster()
    log.info("There is a " + roundCounter + " round. " + whichEngine + " engine will now decide")
    if (activeListOne) {
      val engine = enginesOne(whichEngine)
      engine ! TimeOutMessage(timeOutMessage.chessboardFen, timeOutMessage.duration)
    } else {
      val engine = enginesTwo(whichEngine)
      engine ! TimeOutMessage(timeOutMessage.chessboardFen, timeOutMessage.duration)
    }
  }

  def UpdateChessboardOrTellIsItDraw(answer: MessageBack): Boolean= {
    val fen: FenGenerator = new FenGenerator(chessboard)
    val halfMoveShouldBroke = fen.insertMove(answer.message)
    log.info("halfmove is now " + halfMoveCounter)
    if(halfMoveShouldBroke){
      halfMoveCounter = 0
    } else {
      halfMoveCounter += 1
    }
    if(halfMoveCounter >= 50){
      log.info(" THERE WAS A DRAW!!!!!!") // Example: "7K/7P/5q2/8/4k3/8/8/8 w - -"
      return true
    }
    chessboard = fen.returnFenStringPositions()
    log.info(s"move: ${answer.message} ,chessboard: $chessboard, halfmove: $halfMoveCounter")
    false
  }

  def extractMoveWhichShouldBeTakenInThisGame(): MessageBack = {
    var answerExist :MessageBack = null
    var fenGenerator : FenGenerator = new FenGenerator()
    if(new FenGenerator(chessboard).isMoveExistForActivePlayer(answers(0).message)) {
      answerExist = answers(0)
    } else {
      log.info(answers(0) + " was removed cause was not exist for that player")
    }

    if (answerExist == null) {
      answerExist = MessageBack(answers(0).engineName, "a1a1") // default move which means lost the game - couldnt be null cause it was broke forward game
    }

    answerExist
  }

  def tellForOtherHalfOfEnginesToStartCounting(): Unit = {
    answers.clear()
    activeListOne = !activeListOne
    self ! TimeOutMessage(chessboard, valOfGameRule)
  }
}
