name := "fabricator"

version := "1.0"

scalaVersion := "2.10.2"

resolvers += "Typesafe Simple Repository" at "http://repo.typesafe.com/typesafe/simple/maven-releases/"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.2.0" exclude("org.scala-stm", "scala-stm_2.10.0"),
  "junit"        % "junit" % "4.8" % "test" exclude("org.scala-stm", "scala-stm_2.10.0"),
  "org.scalatest" %% "scalatest" % "1.9.1" % "test" exclude("org.scala-stm", "scala-stm_2.10.0"),
  "com.github.nscala-time" %% "nscala-time" % "1.2.0" exclude("org.scala-stm", "scala-stm_2.10.0")
)