name := "training-scala"

version := "0.1"

scalaVersion := "2.12.8"

scalacOptions += "-Ypartial-unification"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.6.0", 
  "com.typesafe" % "config" % "1.3.4")
