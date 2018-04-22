package simpleChessManagmentActor.actorimplementation

import chess.amqp.message.EngineEloPair
import org.scalatest.{BeforeAndAfter, FunSpec}

import scala.collection.mutable.ListBuffer

class MessageExtractionMethodsTest extends FunSpec with BeforeAndAfter {

  var names = Seq[String]("senpai","stockfish","gull3","greko")
  var elo: Seq[EngineEloPair] = _
  var answers: ListBuffer[MessageBack] = _



  before {
    this.elo = eloMethod
    this.answers = answers

    def eloMethod = {
      var eloList = Seq.empty[EngineEloPair]
      var eloValue = 1000
      println(names)
      names.foreach(name => {
        var engineEloElement = new EngineEloPair()
        engineEloElement.setEloValue(eloValue)
        engineEloElement.setEngineName(name)
        eloList :+= engineEloElement
        eloValue += 200
      })

      names.foreach(name => {
        var engineEloElement = new EngineEloPair()
        engineEloElement.setEloValue(eloValue)
        engineEloElement.setEngineName(name)
        eloList :+= engineEloElement
        eloValue -= 200
      })
      eloList
    }

    def answers = {
      var answers :ListBuffer[MessageBack] = ListBuffer()
      answers += new MessageBack("senpai","b")      // 1000
      answers += new MessageBack("stockfish", "a")  // 1200
      answers += new MessageBack("gull3","a")        // 1400
      answers += new MessageBack("greko","c")       // 1600
    }
  }

  describe("Elo Voting extract works fine") {
    it("and it extract two lower elo with same option") {
      val messageExtractionMethods = new MessageExtractionMethods(answers,elo)
      var message = messageExtractionMethods.extractMessageInAEloVotingApproach()
      assert(message.message === "a")
    }
  }

  describe("Elo Simple extract works fine") {
    it("cause it gets highest elo value"){
      val messageExtractionMethods = new MessageExtractionMethods(answers,elo)
      var message = messageExtractionMethods.extractMessageInAEloSimpleApproach()
      assert(message.message === "c")
    }
  }

  describe("Random Approach extract works fine") {
    it("cause it takes random value"){
      val messageExtractionMethods = new MessageExtractionMethods(answers,elo)
      var message = messageExtractionMethods.extractMessageInARandomApproach()
    }
  }

  describe("Elo Distribution extract works fine") {
    it("cause it takes exact value as it should") {
      val messageExtractionMethods = new MessageExtractionMethods(answers, elo)
      var message = messageExtractionMethods.decideWhichEloShouldBeGet(elo, 500)
      assert(message.message === "b")

      message = messageExtractionMethods.decideWhichEloShouldBeGet(elo, 2000)
      assert(message.message === "a")
    }

    it("cause it extract value on narrow points") {
      val messageExtractionMethods = new MessageExtractionMethods(answers, elo)
      var message = messageExtractionMethods.decideWhichEloShouldBeGet(elo, 999)
      assert(message.message === "b")

      message = messageExtractionMethods.decideWhichEloShouldBeGet(elo, 1000)
      assert(message.message === "a")
    }
  }

}
