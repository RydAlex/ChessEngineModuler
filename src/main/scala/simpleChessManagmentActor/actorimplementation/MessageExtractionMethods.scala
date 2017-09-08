package simpleChessManagmentActor.actorimplementation

import chess.amqp.message.EngineEloPair
import com.typesafe.scalalogging.Logger
import utils.VotingHolder
import scala.util.control.Breaks._

import scala.collection.mutable.ListBuffer
import scala.util.Random

class MessageExtractionMethods(answers :ListBuffer[MessageBack], elo: Seq[EngineEloPair] ) {

  val log = Logger("MessageExtractionMethods")

  def extractMessageInAEloSimpleApproach(): MessageBack = {
    var eloOfActualMessage = 0
    var chosenMessage: MessageBack = null
    answers.foreach(message => {
      elo.foreach(engineEloVal => {
        if(engineEloVal.getEngineName.equals(message.engineName)){
          if(engineEloVal.getEloValue > eloOfActualMessage){
            chosenMessage = message
            eloOfActualMessage = engineEloVal.getEloValue
          }
        }
      })
    })
    chosenMessage
  }

  def extractMessageInAEloVotingApproach(): MessageBack = {
    var messageToReturn: MessageBack = null
    var elements = ListBuffer[VotingHolder]()
    for(message <- answers){
      breakable {
        for (engineEloVal <- elo) {
          if (engineEloVal.getEngineName.equals(message.engineName)) {
            var isFound = false
            for (element <- elements) {
              if (element.voteMessage.message.equals(message.message)) {
                element.voteMessage.engineName = element.voteMessage.engineName + "_" + message.engineName
                element.voteWeight += engineEloVal.getEloValue
                isFound = true

              }
            }
            if (!isFound) {
              var messageWithWeight = new VotingHolder(message, engineEloVal.getEloValue)
              elements += messageWithWeight
            }
            break
          }
        }
      }
    }
    messageToReturn = elements.minBy(- _.voteWeight).voteMessage
    log.info("ELO VOTE MODE: answer: " + messageToReturn)
    messageToReturn
  }

  def extractMessageInAEloDistributionApproach(): MessageBack = {
    var seed = 0
    var eloPairs  = ListBuffer[EngineEloPair]()
    for(answer <- answers){
      breakable {
        for(engineEloVal <- elo){
          if(engineEloVal.getEngineName.equals(answer.engineName)){
            eloPairs += engineEloVal
            seed += engineEloVal.getEloValue
            break
          }
        }
      }
    }
    val valueOfRandomEloDist = Random.nextInt(seed)
    decideWhichEloShouldBeGet(eloPairs,valueOfRandomEloDist)
  }

  def decideWhichEloShouldBeGet(eloPairs: Seq[EngineEloPair], valueOfRandomEloChoose: Integer ): MessageBack = {
    var messageToReturn: MessageBack = null
    var valueOfRandomEloDist = valueOfRandomEloChoose
    for(enginesElo <- eloPairs){
      if(valueOfRandomEloDist < enginesElo.getEloValue){
        for(message <- answers){
          if(enginesElo.getEngineName.equals(message.engineName)){
            log.info("ELO DIST MODE -> choosen msg: " + message)
            return message
          }
        }
      } else {
        valueOfRandomEloDist = valueOfRandomEloDist - enginesElo.getEloValue
      }
    }
    null
  }

  def extractMessageInARandomApproach() : MessageBack = {
    val randomNumber = Random.nextInt(answers.length)
    log.info("RANDOM MODE answer: " +answers(randomNumber) )
    //strange for caused by that answers and names are set in different order
    for(engineMessage <- answers){
      if(engineMessage.engineName.equals(answers(randomNumber).engineName)){
        return engineMessage
      }
    }
    null
  }

}
