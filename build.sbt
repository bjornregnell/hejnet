lazy val appname = "kodaloss"
lazy val versnum = "0.0.1"

scalaVersion := "2.12.5"
version      := s"$versnum-SNAPSHOT"
name := appname
assemblyJarName in assembly := s"$appname-$versnum.jar"
scalacOptions ++= Seq(
      "-encoding", "UTF-8",
      "-unchecked",
      "-deprecation",
      "-Xfuture",
      "-Yno-adapted-args",
      "-Ywarn-dead-code",
      "-Ywarn-numeric-widen",
      "-Ywarn-value-discard",
      "-Ywarn-unused"
    )
libraryDependencies += "jline" % "jline" % "2.14.5"
