import sbt.Keys._
import sbt.Level

name := "ChessEngineModuler"
logLevel := Level.Warn

mainClass in Compile := Some("simpleChessManagmentActor.ChessScheduler")

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "com.rabbitmq" % "amqp-client" % "3.6.5",
  "com.typesafe.akka" %% "akka-actor" % "2.4.17",
  "com.typesafe.akka" %% "akka-agent" % "2.4.17",
  "com.typesafe.akka" %% "akka-camel" % "2.4.17",
  "com.typesafe.akka" %% "akka-cluster" % "2.4.17",
  "com.typesafe.akka" %% "akka-cluster-metrics" % "2.4.17",
  "com.typesafe.akka" %% "akka-cluster-sharding" % "2.4.17",
  "com.typesafe.akka" %% "akka-cluster-tools" % "2.4.17",
  "com.typesafe.akka" %% "akka-contrib" % "2.4.17",
  "com.typesafe.akka" %% "akka-multi-node-testkit" % "2.4.17",
  "com.typesafe.akka" %% "akka-osgi" % "2.4.17",
  "com.typesafe.akka" %% "akka-persistence" % "2.4.17",
  "com.typesafe.akka" %% "akka-persistence-tck" % "2.4.17",
  "com.typesafe.akka" %% "akka-remote" % "2.4.17",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.17",
  "com.typesafe.akka" %% "akka-stream" % "2.4.17",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.4.17",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.17",
  "com.typesafe.akka" %% "akka-distributed-data-experimental" % "2.4.17",
  "com.typesafe.akka" %% "akka-typed-experimental" % "2.4.17",
  "com.typesafe" % "config" % "1.3.1",
  "org.postgresql" % "postgresql" % "9.4.1212",
  "org.projectlombok" % "lombok" % "1.16.8",
  "joda-time" % "joda-time" % "2.9.7",
  "org.hamcrest" % "hamcrest-library" % "1.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.typesafe.slick" %% "slick" % "3.2.0"
//"org.scalastyle" %% "scalastyle-sbt-plugin" % "0.8.0"

)