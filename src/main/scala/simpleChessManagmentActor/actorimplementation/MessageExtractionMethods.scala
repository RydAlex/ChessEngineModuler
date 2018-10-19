package simpleChessManagmentActor.actorimplementation

import com.typesafe.scalalogging.Logger

import scala.collection.mutable.ListBuffer
import scala.util.Random

class MessageExtractionMethods(answers :ListBuffer[MessageBack]) {

  val log = Logger("MessageExtractionMethods")

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
