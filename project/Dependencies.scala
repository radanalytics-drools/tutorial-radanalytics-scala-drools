import sbt._

object Dependencies {

    val slf4jVersion = "1.7.5"
    val logbackVersion = "1.1.7"
    val sparkVersion = "2.2.0"
    val sparkTestBaseVersion = "2.2.0_0.8.0"
    val kiegroupVersion = "7.13.0.Final"
    val scalaTestVersion = "3.0.4"
    val scalatraVersion = "2.5.4"
    val radanalyticsJavaLibVersion = "0.0.1-SNAPSHOT"

    val radanalyticsJavaCode = Seq( "io.radanalytics" % "domain-model" % radanalyticsJavaLibVersion )

    val logging = Seq( "org.slf4j" % "slf4j-api" % slf4jVersion, "ch.qos.logback" % "logback-classic" % logbackVersion )

    val loggingTest = Seq( "org.slf4j" % "slf4j-api" % slf4jVersion % "test", "ch.qos.logback" % "logback-classic" % logbackVersion % "test" )

    val spark = Seq( "org.apache.spark" %% "spark-core" % sparkVersion % "provided" )

    val sparkTestBase = Seq( "com.holdenkarau" %% "spark-testing-base" % sparkTestBaseVersion % "test" )

    val drools = Seq( "org.drools" % "drools-core" % kiegroupVersion,
                      "org.drools" % "drools-compiler" % kiegroupVersion )

    val droolsTest = Seq( "org.drools" % "drools-core" % kiegroupVersion % "test",
                          "org.drools" % "drools-compiler" % kiegroupVersion % "test" )

    val kie = Seq( "org.kie" % "kie-api" % kiegroupVersion,
                   "org.kie" % "kie-internal" % kiegroupVersion,
                   "org.kie" % "kie-ci" % kiegroupVersion )

    val kieTest = Seq( "org.kie" % "kie-api" % kiegroupVersion % "test",
                       "org.kie" % "kie-internal" % kiegroupVersion % "test",
                       "org.kie" % "kie-ci" % kiegroupVersion % "test" )

    //    val opennlp = Seq( "org.apache.opennlp" % "opennlp-tools" % opennlpVersion )


    val scalaTest = Seq( "org.scalatest" %% "scalatest" % scalaTestVersion % "test" )

    // TODO - fix versions later
    val scalatra = Seq( "org.scalatra" %% "scalatra" % scalatraVersion,
                        "org.scalatra" %% "scalatra-scalatest" % scalatraVersion % "test",
                        "org.scalatra" %% "scalatra-json" % scalatraVersion,
                        "org.json4s" %% "json4s-jackson" % "3.5.2",
                        "ch.qos.logback" % "logback-classic" % "1.2.3",
                        "org.eclipse.jetty" % "jetty-webapp" % "9.2.19.v20160908",
                        "javax.servlet" % "javax.servlet-api" % "3.1.0" )
}