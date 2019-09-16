scalaVersion in ThisBuild := "2.12.8"

lazy val commonSettings = Seq(
  organization := "com.example",
  version := "0.1.0-SNAPSHOT"
)

lazy val xml_parsing_snippets = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    organization := "com.github.ylobazov",
    name := "smart-xml-analyzer-task",
    version := "0.0.1-SNAPSHOT",

    //util dependencies
    libraryDependencies ++= Seq(
      "org.jsoup" % "jsoup" % "1.11.2",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.8.0",
      "org.scalactic" %% "scalactic" % "3.0.8",
      "org.scalatest" %% "scalatest" % "3.0.8" % "test"
    ),

    mainClass in assembly := Some("com.github.ylobazov.hometask.xmlanalyzer.Main"),

    assemblyJarName in assembly := "smart-xml-analyzer-task.jar",

    fork in Test := true,
    parallelExecution in Test := false,
  ).enablePlugins(AssemblyPlugin)

resolvers in Global ++= Seq(
  "Sbt plugins" at "https://dl.bintray.com/sbt/sbt-plugin-releases",
  "Maven Central Server" at "https://repo1.maven.org/maven2",
  "TypeSafe Repository Releases" at "https://repo.typesafe.com/typesafe/releases/",
  "TypeSafe Repository Snapshots" at "https://repo.typesafe.com/typesafe/snapshots/"
)
