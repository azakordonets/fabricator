logLevel := Level.Warn

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.2" extra("sbtVersion" -> "0.13", "scalaVersion" -> "2.10"))

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.11.2")