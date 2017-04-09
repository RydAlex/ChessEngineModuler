package simpleChessManagmentActor.actorimplementation

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.event.slf4j.Logger
import akka.pattern._
import akka.util.Timeout
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
 * Created by aleksanderr on 23/03/17.
 */
class ActorRunner(actorSystemName : String) {

  val logger = Logger("simpleChessManagmentActor.actorimplementation.ActorRunner")
  val actorSystem = ActorSystem(actorSystemName)

  def createChessActorInSystem(name: String): ActorRef = {
    actorSystem.actorOf(Props(new EngineActor(name)))
  }

//  def countTimeout(fenString: String, time: Int, enginesNames: ActorRef*): Future[List[String]] = {
//    implicit val timeout: Timeout = 15 * time seconds
//
//    val result1 = enginesNames(0) ! new TimeOutMessage(fenString, time)
//
//    val result2 = (enginesNames(1) ? new TimeOutMessage(fenString, time)).mapTo[String] onComplete {
//      case Success(x) => x
//      case Failure(ex) => ex
//    }
//
//    val result3 = (enginesNames(2) ? new TimeOutMessage(fenString, time)).mapTo[String] onComplete {
//      case Success(x) => x
//      case Failure(ex) => ex
//    }
//
//    val result4 = (enginesNames(3) ? new TimeOutMessage(fenString, time)).mapTo[String] onComplete {
//      case Success(x) => x
//      case Failure(ex) => ex
//    }
//
//    for(x <- result1; y <- result2; z <- result3; v <- result4) yield {
//      println(x)
//      println(y)
//      println(z)
//      println(v)
//      List(x, y, z, v)
//    }

    //val result = Await.result(resultGlobal, Duration.Inf)

//    val result1 = enginesNames(0) ? new TimeOutMessage(fenString, time)
//    for(x <- result1) {
//          println(x)
//      }
//    val h = Await.result(result1, Duration.Inf)
//    logger.info("Result is : " + h)
//    h.toString
//  }

  def countDepth(fenString: String, depth: Int, enginesNames: ActorRef*): String = {
    try {
      implicit val timeout: Timeout = 1 minute

      val result1 = enginesNames(0) ? new DepthMessage(fenString, depth)
      val h = Await.result(result1, Duration.Inf)
      logger.info("Result is : " + h)
      h.toString
    } catch {
      case e: AskTimeoutException => "Error"
    }
  }
}
//    implicit val timeout: Timeout = 10 minutes
//    val result1 = actor1 ? new DepthMessage(fenString, depth)
//    val result2 = actor2 ? new DepthMessage(fenString, depth)
//    val result3 = actor3 ? new DepthMessage(fenString, depth)
//    val result4 = actor4 ? new DepthMessage(fenString, depth)
//
//    for(x <- result1; y <- result2; z <- result3; v <- result4) {
//      println(s"$x $y $z $v")

//  (actor1 ? TimeOutMessage).mapTo[String] {
//    case Success(x) => x
//    case Failure(ex: Throwable) => "Not applicable"
//  }
//
//  (actor2 ? TimeOutMessage).mapTo[String] {
//    case Success(x) => x
//    case Failure(ex: Throwable) => "Not applicable"
//  }
//
//  (actor3 ? TimeOutMessage).mapTo[String] {
//    case Success(x) => x
//    case Failure(ex: Throwable) => "Not applicable"
//  }
//
//  (actor4 ? TimeOutMessage).mapTo[String] {
//    case Success(x) => x
//    case Failure(ex: Throwable) => "Not applicable"
//  }
//
//  val result1 = (actor1 ? TimeOutMessage).mapTo[String]
//  val result2 = (actor2 ? TimeOutMessage).mapTo[String]
//  val result3 = (actor3 ? TimeOutMessage).mapTo[String]
//  val result4 = (actor4 ? TimeOutMessage).mapTo[String]
//
//  for(x <- result1; y <- result2; z <- result3; v <- result4) {
//      println(x)
//      println(y)
//      println(z)
//      println(v)
//  }
