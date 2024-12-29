name := "config test"

scalaVersion := "2.13.15"

scalacOptions ++= Seq(
  "-deprecation",
  "-Xfatal-warnings",
  "-Ywarn-value-discard",
  "-Xlint:missing-interpolator"
)

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.4.3",
  "com.github.pureconfig" %% "pureconfig" % "0.17.8"
)