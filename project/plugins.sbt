logLevel := Level.Warn

resolvers += Classpaths.sbtPluginReleases

resolvers += Classpaths.typesafeReleases

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.2" extra("sbtVersion" -> "0.13", "scalaVersion" -> "2.10"))

addSbtPlugin("org.scoverage" %% "sbt-scoverage" % "1.0.1")

addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.0.0.BETA1")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.12.0")

// Add the following to have Git manage your build versions
resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven"

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.6.4")
