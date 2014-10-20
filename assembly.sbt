import AssemblyKeys._

assemblySettings

lazy val buildSettings = Seq(
  version := "0.1-SNAPSHOT",
  organization := "com.github",
  scalaVersion := "2.10.1"
)

jarName in assembly := "fabricator.jar"