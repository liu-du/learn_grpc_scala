import Dependencies._
import scalapb.compiler.Version.scalapbVersion

ThisBuild / scalaVersion := "2.13.1"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "learn_grpc_scala",
    libraryDependencies ++= Seq(
      // scalapb
      "com.thesamet.scalapb" %% "scalapb-runtime" % scalapbVersion % "protobuf",
      // grpc
      "io.grpc" % "grpc-netty-shaded" % scalapb.compiler.Version.grpcJavaVersion, // include SSL libraries
      "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,
      // scalatest
      scalaTest % Test
    )
  )

PB.targets in Compile := Seq(
  scalapb.gen(flatPackage = true) -> (sourceManaged in Compile).value
)

fork := true
outputStrategy := Some(StdoutOutput)
