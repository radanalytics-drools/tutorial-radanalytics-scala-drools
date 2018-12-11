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
                                "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
                                "OCP Nexus 3" at "http://nexus3-nexus.192.168.64.77.nip.io/repository/radanalytics-drools/")

// don't run tests when building the fat jars
test in assembly := {}

//@formatter:off
lazy val root = ( project in file( "." ) ).aggregate( controller )

lazy val controller = ( project in file( "controller" ) )
                                    .settings( libraryDependencies ++= radanalyticsJavaCode ++ spark ++ kie ++ drools ++ scalatra ++ logging  ++ scalaTest )

//@formatter:off