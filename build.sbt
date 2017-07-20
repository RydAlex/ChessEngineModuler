import sbt.Keys._

name := "ChessEngineModuler"

logLevel := Level.Info

version := "1.0"

mainClass in (Compile, run) := Some("chess.amqp.receiver.AMQPConsumer")

scalaVersion := "2.12.2"

lazy val root = (project in file("chessEngineModuler")).enablePlugins(JavaServerAppPackaging)

fork in run := true

libraryDependencies ++= Seq(
  "com.rabbitmq" % "amqp-client" % "3.6.5",
  "org.mockito" % "mockito-core" % "2.7.22" % "test",
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
  "org.hibernate" % "hibernate-core" % "5.2.9.Final",
  "org.springframework.boot" % "spring-boot-starter-web" % "1.5.3.RELEASE",
  "org.springframework" % "spring-core" % "4.3.8.RELEASE",
  "org.springframework" % "spring-context" % "4.3.8.RELEASE",
  "org.springframework" % "spring-web" % "4.3.8.RELEASE",
  "org.springframework" % "spring-beans" % "4.3.8.RELEASE",
  "org.springframework" % "spring-context-support" % "4.3.8.RELEASE",
  "org.springframework" % "spring-orm" % "4.3.8.RELEASE",
  "org.springframework" % "spring-aop" % "4.3.8.RELEASE",
  "org.projectlombok" % "lombok" % "1.16.16",
  "joda-time" % "joda-time" % "2.9.7",
  "org.apache.commons" % "commons-lang3" % "3.1",
  "org.hamcrest" % "hamcrest-library" % "1.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.typesafe.slick" %% "slick" % "3.2.0"
)
