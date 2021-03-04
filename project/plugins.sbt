logLevel := Level.Warn

resolvers += Classpaths.sbtPluginReleases

resolvers += Classpaths.typesafeReleases

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.6.0")
addSbtPlugin("org.scoverage" %% "sbt-coveralls" % "1.2.7")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.10")

// Add the following to have Git manage your build versions
resolvers += "jgit-repo" at "https://download.eclipse.org/jgit/maven"
resolvers += Resolver.url("scoverage-bintray", url("https://dl.bintray.com/sksamuel/sbt-plugins/"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "1.0.0")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.8")
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "2.0.0")
