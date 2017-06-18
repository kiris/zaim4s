lazy val root = (project in file("."))
  .settings(
    name := "zaim4s",
    organization := "com.github.kiris",
    version := "0.1",
    scalaVersion := "2.12.2",
    crossScalaVersions := Seq("2.11.8", "2.12.2")

  )

resolvers ++= Seq(
  "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases/",
  "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.6.0-M2",
  "net.databinder.dispatch" %% "dispatch-core" % "0.13.0",
  "com.github.tototoshi" %% "play-json-naming" % "1.2.0-SNAPSHOT"
)
