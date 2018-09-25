organization := "io.radanalytics.tutorial.scala.drools"
name := "rules"
version := "0.0.1-SNAPSHOT"

aggregate in assembly := false

// this gets rid of the _$SCALA_VERSION postfix to the artifact name, we don't need that for kjars
crossPaths := false