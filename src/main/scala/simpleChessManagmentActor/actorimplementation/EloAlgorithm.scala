package simpleChessManagmentActor.actorimplementation

import AMQPManagment.DatabaseConnections.DAO.CurrentEloDAO
import simpleChessManagmentActor.actorimplementation.GameResult.result

/**
  * Created by aleksanderr on 12/04/17.
  */
object EloAlgorithm {

  def getElo(engineName: String) : Int = {
    1000
    // getting it from database
  }


  def myGameResultRate(result: result) : Double= {
    var gameRate :Double = 0
    if(result.equals(GameResult.WIN)){
      gameRate = 1
    } else if(result.equals(GameResult.DRAW)){
      gameRate = 0.5
    } else if(result.equals(GameResult.LOSE)){
      gameRate = 0
    }
    gameRate
  }

  def calculateRating(engineName :String, opponentEngineName: String, result: GameResult.result): Int = {

    def calculateEloDelta(engineName: String, opponentEngineName: String, result: GameResult.result) : Int = {
      val myRating = getElo(engineName)
      val opponentRating = getElo(opponentEngineName)
      val myChanceToWin = 1 / ( 1 + Math.pow(10, (opponentRating - myRating) / 400))
      val eloDelta = Math.round(32 * (myGameResultRate(result) - myChanceToWin))
      eloDelta.toInt
    }

    var rating = 1000
    rating = rating + calculateEloDelta(engineName, opponentEngineName, result)
    rating
  }
}
