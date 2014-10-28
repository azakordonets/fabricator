name := "fabricator"

version := "1.0"

scalaVersion := "2.10.2"

resolvers += "Typesafe Simple Repository" at "http://repo.typesafe.com/typesafe/simple/maven-releases/"

resolvers += "JCenter" at "http://jcenter.bintray.com/"


libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.2.0" ,
  "org.testng" % "testng" % "6.8.8" % "test",
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test",
  "com.github.nscala-time" %% "nscala-time" % "1.2.0",
  "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
  "org.slf4j" % "slf4j-api" % "1.7.1",
  "org.slf4j" % "log4j-over-slf4j" % "1.7.1",  // for any java classes looking for this
  "ch.qos.logback" % "logback-classic" % "1.0.3",
  "com.google.inject" % "guice" % "3.0"
)

unmanagedResourceDirectories in Compile += { baseDirectory.value / "src/main/resources" }