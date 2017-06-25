organization := "com.github.kiris"
name := "zaim4s"

scalaVersion := "2.12.2"

homepage := Some(url("http://github.com/kiris/zaim4s"))
licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))


resolvers ++= Seq(
  "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases/",
  "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.6.0-M2",
  "net.databinder.dispatch" %% "dispatch-core" % "0.13.0",
  "com.github.tototoshi" %% "play-json-naming" % "1.2.0",

  "org.slf4j" % "slf4j-api" % "1.7.25" % "compile",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % "test"
)

publishMavenStyle := true
publishArtifact in Test := false
publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)

sonatypeProfileName := "com.github.kiris"

scmInfo := Some(
  ScmInfo(
    url("https://github.com/kiris/zaim4s"),
    "scm:git:git@github.com:kiris/zaim4s.git"
  )
)

developers := List(
  Developer(
    id = "kiris",
    name = "Yoshiaki Iwanaga",
    email = "kiris60@gmail.com",
    url = url("http://kiris.github.com")
  )
)

import ReleaseTransformations._
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  ReleaseStep(action = Command.process("publishSigned", _)),
  setNextVersion,
  commitNextVersion,
  ReleaseStep(action = Command.process("sonatypeReleaseAll", _)),
  pushChanges
)
