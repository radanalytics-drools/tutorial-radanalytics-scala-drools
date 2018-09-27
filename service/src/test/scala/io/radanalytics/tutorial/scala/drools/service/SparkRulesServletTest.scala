package io.radanalytics.tutorial.scala.drools.service

import org.scalatest.FlatSpecLike
import org.scalatra.test.scalatest.ScalatraSuite

class SparkRulesServletTest extends FlatSpecLike with ScalatraSuite {

    addServlet( classOf[ SparkRulesServlet ], "/*" )

    "GET request for /" should "return 200" in {
        //@formatter:off
        get( "/" ) {
            status should equal ( 200 )
            body should equal ( "The Spark + Drools application is up and running!" )
        }
        //@formatter:on
    }

    "POST request for /execute" should "return 201" in {
        //@formatter:off
        post( "/execute" ) {
            status should equal ( 201 )
            body should equal (  "Executing your Spark + Drools job" )
        }
        //@formatter:on
    }

}