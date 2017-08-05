package simpleChessManagmentActor.actorimplementation

import chess.amqp.message.EngineEloPair
import com.typesafe.scalalogging.Logger
import utils.VotingHolder

import scala.collection.mutable.ListBuffer
import scala.util.Random

class MessageExtractionMethods(name: Seq[String], answers :ListBuffer[MessageBack], elo: Seq[EngineEloPair] ) {

  val log = Logger("MessageExtractionMethods")

  def extractMessageInAEloSimpleApproach(): MessageBack = {
    var eloOfActualMessage = 0
    var chosenMessage: MessageBack = null
    answers.foreach(message => {
      elo.foreach(engineEloVal => {
        if(engineEloVal.getEngineName.equals(message.engineName)){
          if(engineEloVal.getEloValue > eloOfActualMessage){
            //TODO: Add Trend recognition
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
      for(engineEloVal <- elo) {
        if(engineEloVal.getEngineName.equals(message.engineName)){
          var isFound = false
          for(element <- elements){
            if(element.voteMessage.message.equals(message.message)){
              element.voteWeight += engineEloVal.getEloValue
              isFound = true
            }
          }
          if(!isFound){
            var messageWithWeight = new VotingHolder(message, engineEloVal.getEloValue)
            elements += messageWithWeight
          }
        }
      }
    }
    messageToReturn = elements.minBy(- _.voteWeight).voteMessage
    messageToReturn
  }

  def extractMessageInAEloDistributionApproach(): MessageBack = {
    var seed = 0
    var eloPairs  = Seq[EngineEloPair]()
    for(message <- answers){
      for(engineEloVal <- elo){
        if(engineEloVal.getEngineName.equals(message.engineName)){
          eloPairs :+ engineEloVal
          seed += engineEloVal.getEloValue
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
    log.info(name + " answer is taken from " + answers(randomNumber).engineName)
    val answer = answers(randomNumber)
    answer
  }

}
