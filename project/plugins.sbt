logLevel := Level.Warn

resolvers += Classpaths.sbtPluginReleases

resolvers += Classpaths.typesafeReleases

addSbtPlugin("org.scoverage"  % "sbt-scoverage" % "2.0.8")
addSbtPlugin("org.scoverage" %% "sbt-coveralls" % "1.3.11")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "2.1.1")

// Add the following to have Git manage your build versions
resolvers += "jgit-repo" at "https://download.eclipse.org/jgit/maven"

resolvers += Resolver.url("scoverage-bintray", url("https://dl.bintray.com/sksamuel/sbt-plugins/"))(
  Resolver.ivyStylePatterns
)

addSbtPlugin("com.github.sbt" % "sbt-git"      % "2.0.1")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.9.21")
addSbtPlugin("com.github.sbt" % "sbt-pgp"      % "2.2.1")

ThisBuild / libraryDependencySchemes ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
)
