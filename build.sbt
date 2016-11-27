import sbt.Keys._
import scoverage.ScoverageSbtPlugin.ScoverageKeys

crossScalaVersions := Seq("2.10.4", "2.11.4")

scalacOptions += "-target:jvm-1.7"

name := "fabricator"

version := "2.1.3"

organization := "com.github.azakordonets"

licenses +=("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

homepage := Some(url("https://github.com/azakordonets/fabricator"))

organizationHomepage := Some(url("https://biercoff.com"))

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
  Some("snapshots" at nexus + "content/repositories/snapshots")
  else
  Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false}

unmanagedResourceDirectories in Compile += {
  baseDirectory.value / "src/main/resources"
}

pomExtra := <scm>
  <url>git@github.com:azakordonets/fabricator.git</url>
  <connection>scm:git:git@github.com:azakordonets/fabricator.git</connection>
</scm>
  <developers>
    <developer>
      <id>azakordonets</id>
      <name>Andrew Zakordonets</name>
      <url>http://biercoff.com</url>
    </developer>
  </developers>

ScoverageKeys.coverageMinimum := 90

ScoverageKeys.coverageFailOnMinimum := true

ScoverageKeys.coverageHighlighting := {
  if (scalaBinaryVersion.value == "2.10") true else false
}

Seq(bintrayPublishSettings:_*)

bintray.Keys.repository in bintray.Keys.bintray := "Fabricator"

bintray.Keys.bintrayOrganization in bintray.Keys.bintray := None

resolvers += "Typesafe Simple Repository" at "http://repo.typesafe.com/typesafe/simple/maven-releases/"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "JCenter" at "http://dl.bintray.com/"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

resolvers += Classpaths.sbtPluginReleases

resolvers += Resolver.typesafeRepo("releases")

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.4.8",
  "org.testng" % "testng" % "6.9.4",
  "org.scalatest" %% "scalatest" % "3.0.0-SNAP4" % "test",
  "com.github.nscala-time" %% "nscala-time" % "1.2.0",
  "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
  "org.slf4j" % "slf4j-api" % "1.7.1",
  "org.reflections" % "reflections" % "0.9.10",
  "ch.qos.logback" % "logback-classic" % "1.0.3",
  "org.assertj" % "assertj-core" % "3.1.0",
  "com.spatial4j" % "spatial4j" % "0.4.1",
  "org.iban4j" % "iban4j" % "2.1.1",
  "commons-validator" % "commons-validator" % "1.4.0",
  "com.google.inject" % "guice" % "3.0",
  "com.github.tototoshi" % "scala-csv_2.10" % "1.1.2",
  "com.google.zxing" % "core" % "3.2.0"

)




