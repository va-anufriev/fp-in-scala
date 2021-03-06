name := "fp-in-scala"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.13.4"

// scalac options come from the sbt-tpolecat plugin so need to set any here

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.1" cross CrossVersion.full)

lazy val catsCore = "org.typelevel" %% "cats-core" % "2.3.1"
lazy val catsEffect = "org.typelevel" %% "cats-effect" % "2.3.1"
lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.2" % Test

lazy val cats = (project in file("cats"))
  .settings(
    libraryDependencies ++= Seq(catsCore, catsEffect, scalaTest)
  )

lazy val root = (project in file("."))
  .aggregate(
    cats
  )
