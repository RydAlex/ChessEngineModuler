package simpleChessManagmentActor

import engineprocessor.core.enginemechanism.EngineAvailabilityScanner

import scala.util.Random

/**
  * Created by aleksanderr on 16/04/17.
  */
class GameMultipleEngineSetCreator {

  def getMultipleEnginesForGame(numberOfEngine: Int): Seq[String] = {
    var returnListOfNames : Seq[String] = Seq[String]()
    val engineList = EngineAvailabilityScanner.getInstance().returnListOfNames()
    println(engineList)
    for(i <- Range(0, numberOfEngine)){
      val allEngineNumber = Random.nextInt(engineList.size())
      returnListOfNames = returnListOfNames :+ engineList.get(allEngineNumber)
      engineList.remove(allEngineNumber)
    }
    returnListOfNames
  }

}
