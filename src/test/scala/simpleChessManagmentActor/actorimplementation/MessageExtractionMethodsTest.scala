package simpleChessManagmentActor.actorimplementation

import org.scalatest.{BeforeAndAfter, FunSpec}

import scala.collection.mutable.ListBuffer

class MessageExtractionMethodsTest extends FunSpec with BeforeAndAfter {

  var names = Seq[String]("senpai","stockfish","gull3","greko")
  var answers: ListBuffer[MessageBack] = _

  before {
    this.answers = answers

    def answers = {
      var answers :ListBuffer[MessageBack] = ListBuffer()
      answers += new MessageBack("senpai","b")      // 1000
      answers += new MessageBack("stockfish", "a")  // 1200
      answers += new MessageBack("gull3","a")        // 1400
      answers += new MessageBack("greko","c")       // 1600
    }
  }

  describe("Random Approach extract works fine") {
    it("cause it takes random value"){
      val messageExtractionMethods = new MessageExtractionMethods(answers)
      var message = messageExtractionMethods.extractMessageInARandomApproach()
    }
  }

}
