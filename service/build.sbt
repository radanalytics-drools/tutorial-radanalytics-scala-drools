organization := "io.radanalytics.tutorial.scala.drools"
name := "service"
version := "0.0.1-SNAPSHOT"

mainClass in(Compile, run) := Some( "io.radanalytics.tutorial.scala.drools.Main" )

// docs for why this is necessary are here: https://github.com/scalatra/sbt-scalatra
enablePlugins( JavaAppPackaging )
enablePlugins( JettyPlugin )
enablePlugins( ScalatraPlugin )

// this section handles conflicts when building the "fat jar"
assemblyMergeStrategy in assembly := {
    case PathList( "META-INF", "MANIFEST.MF" ) => MergeStrategy.discard
    case PathList( "reference.conf" ) => MergeStrategy.concat
    case PathList( "META-INF", "kie.conf" ) => MergeStrategy.concat
    //    case PathList( "META-INF", xs@_* ) => MergeStrategy.first
    case x => MergeStrategy.last
}