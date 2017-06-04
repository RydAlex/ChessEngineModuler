package simpleChessManagmentActor.actorimplementation

/**
  * Created by aleksanderr on 13/04/17.
  */

object TypeOfGame extends Enumeration{
  type gameMetodology = Value
  val RANDOM, WEIGHT, ELO, CLUSTER_ELO = Value
}

object GameResult extends Enumeration{
  type result = Value
  val WIN, LOSE, DRAW = Value
}
