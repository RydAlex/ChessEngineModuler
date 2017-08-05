package utils

import simpleChessManagmentActor.actorimplementation.MessageBack

class VotingHolder(messageBack: MessageBack, weight: Integer) {
  var voteMessage :MessageBack = messageBack
  var voteWeight :Int = weight
}
