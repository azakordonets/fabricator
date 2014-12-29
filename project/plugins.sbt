logLevel := Level.Warn

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.2" extra("sbtVersion" -> "0.13", "scalaVersion" -> "2.10"))

addSbtPlugin("org.scoverage" %% "sbt-scoverage" % "1.0.1")

addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.0.0.BETA1")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.12.0")
