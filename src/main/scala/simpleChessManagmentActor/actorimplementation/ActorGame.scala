package simpleChessManagmentActor.actorimplementation

import akka.actor._
import akka.event.Logging
import engineprocessor.core.enginemechanism.FenGenerator

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Random}


/**
  * Created by aleksanderr on 09/04/17.
  */
class ActorGame(system: ActorSystem, name: Seq[String]) extends Actor {


  val log = Logging(context.system, this)
  var timeOutGameOrAnother: Boolean = true
  var typeOfGame: TypeOfGame.gameMetodology = TypeOfGame.RANDOM
  var valOfGameRule: Int = 0
  var halfMoveCounter = 0
  var chessboard: String = ""
  var senderRef: ActorRef = null
  var listLength: Int = 0
  var answers :ListBuffer[MessageBack] = ListBuffer()
  var activeListOne = true
  var enginesOne :List[ActorRef] = List[ActorRef]()
  var enginesTwo :List[ActorRef] = List[ActorRef]()


  def receive = {
    case message: MessageBack =>
      log.info(name + "Msg from engine received from " + message.engineName +  " no :" + message.id)
      answers += message
      if(answers.length == listLength/2) {
        self ! AssumingMessage()
      }

    case assumingMsg: AssumingMessage =>
      logMessageOnEnterance(assumingMsg)
      val answer :MessageBack = ExtractMoveWhichShouldBeTakenInThisGame()
      val isCheckmate = new FenGenerator(chessboard).isMoveACheckmate(answer.message)
      if(isCheckmate) {
        log.info(name + " - " + answer.engineName + " LOST!")
        self ! EndGame()
        log.info("Heyo i am here...")
      }
      else {
        UpdateChessboardOrTellIsItDraw(answer)
        tellForOtherHalfOfEnginesToStartCounting()
      }


    case depthMessage: DepthMessage =>
      log.info(name + " Depth msg received and will be send to engines:" + depthMessage.id)
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
      log.info(name + " Timeout msg received :" + timeOutMessage.id)
      if(activeListOne){
        for (engine <- enginesOne){
          engine ! TimeOutMessage(timeOutMessage.chessboardFen, timeOutMessage.duration)
        }
      } else {
        for (engine <- enginesTwo){
          engine ! TimeOutMessage(timeOutMessage.chessboardFen, timeOutMessage.duration)
        }
      }

    case endGame: EndGame =>
      log.info(name + " Lets kill everything... no:")
      for (engine <- enginesOne){
        engine ! PoisonPill
      }
      for (engine <- enginesTwo){
        engine ! PoisonPill
      }
      senderRef ! EndGame

     case initGame: InitGame =>
       log.info(name + " InitGame Command received :")
       this.typeOfGame = initGame.typeOfGame
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
      while(enginesOne.length + enginesTwo.length != listLength){}
      self ! new TimeOutMessage(chessboard,timeoutRule.timeout)

    case depthRule :StartNewGameWithDepthRule =>
      chessboard = new FenGenerator(depthRule.chessboardFen).returnFenStringPositions()
      timeOutGameOrAnother = false
      valOfGameRule = depthRule.depth
      log.info(name + " game with depth rule should be started shortly :" + depthRule.id)
      while(enginesOne.length + enginesTwo.length != listLength){}
      self ! new TimeOutMessage(chessboard,depthRule.depth)

    case newActor: CreateNewActorInFirstGroup =>
      log.info(name + " - " + newActor.engineName + " actor is created in first group - msg no :" + newActor.id)
      enginesOne = system.actorOf(Props(new EngineActor(newActor.engineName))) :: enginesOne

    case newActor: CreateNewActorInSecondGroup =>
      log.info(name + " - " + newActor.engineName + " actor is created in second group - msg no :" + newActor.id)
      enginesTwo = system.actorOf(Props(new EngineActor(newActor.engineName))) :: enginesTwo
  }


  def UpdateChessboardOrTellIsItDraw(answer: MessageBack): Unit = {
    val fen: FenGenerator = new FenGenerator(chessboard)
    val halfMoveShouldBroke = fen.insertMove(answer.message)
    if(halfMoveShouldBroke){
      halfMoveCounter = 0
    } else {
      halfMoveCounter += 1
    }
    log.info(name + " Half Move Counter is now at : " + halfMoveCounter)
    if(halfMoveCounter == 50){
      log.info(name + " THERE WAS A DRAW!!!!!!")
    }
    chessboard = fen.returnFenStringPositions()
    log.info(s"$name - move: ${answer.message} ,chessboard: $chessboard")
  }

  def logMessageOnEnterance(assumingMsg: AssumingMessage) = {
    log.info(name + " Enough message receive - lets assume :" + assumingMsg.id)
    log.info(s" All answers $answers")
  }

  def ExtractMoveWhichShouldBeTakenInThisGame(): MessageBack = {
    var answer :MessageBack = null
    if (typeOfGame.equals(TypeOfGame.RANDOM)) {
      answer = extractMessageInARandomApproach()
    } else if (typeOfGame.equals(TypeOfGame.ELO)) {
      answer = extractMessageInAEloApproach()
    } else if (typeOfGame.equals(TypeOfGame.CLUSTER_ELO)) {
      answer = extractMessageInAClusterEloApproach()
    } else if (typeOfGame.equals(TypeOfGame.WEIGHT)) {
      answer = extractMessageInAWeightApproach()
    }
    answer
  }

  def extractMessageInAEloApproach(): MessageBack = {
    null
  }

  def extractMessageInAClusterEloApproach(): MessageBack = {
    null
  }

  def extractMessageInAWeightApproach(): MessageBack = {
    null
  }

  def extractMessageInARandomApproach() : MessageBack = {
    val randomNumber = Random.nextInt(answers.length)
    log.info(name + " answer is taken from " + answers(randomNumber).engineName)
    val answer = answers(randomNumber)
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
