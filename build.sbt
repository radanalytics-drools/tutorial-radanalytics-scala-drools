import sbt._
import Dependencies._

organization := "io.radanalytics.tutorial.scala.drools"
name := "tutorial-scala-drools"
version := "0.0.1-SNAPSHOT"
scalaVersion in ThisBuild := "2.11.11"

// reccommended from: https://github.com/holdenk/spark-testing-base/blob/master/README.md
fork in Test := true
javaOptions ++= Seq( "-Xms512M", "-Xmx2048M", "-XX:MaxMetaspace=1024M", "-XX:+CMSClassUnloadingEnabled" )

// necessary for the Drools KJAR to work
publishMavenStyle := true

// Dependency resolution confifguration
resolvers += Resolver.sbtPluginRepo( "releases" )
resolvers += Classpaths.typesafeReleases
resolvers in ThisBuild ++= Seq( "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases",
                                "Spray IO Repository" at "http://repo.spray.io/",
                                "Maven Central" at "https://repo1.maven.org/maven2/",
                                "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/" )

//@formatter:off
lazy val root = ( project in file( "." ) ).aggregate( model, rules, service )

lazy val model = ( project in file( "model" ) ).settings( libraryDependencies ++= scalaTest )


lazy val rules = ( project in file( "rules" ) )
                                    .dependsOn( model )
                                    .settings( libraryDependencies ++= kieTest ++ droolsTest ++ scalaTest ++ loggingTest )

lazy val service = ( project in file( "service" ) )
                                    .dependsOn( model, rules ) //TODO - make a branch to show dynamic rules from DM7
                                    .settings( libraryDependencies ++= spark ++ scalaTest ++ scalatra )

//@formatter:off