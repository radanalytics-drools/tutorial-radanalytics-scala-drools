package io.radanalytics.tutorial.scala.drools.service

import org.scalatest.FlatSpecLike
import org.scalatra.test.scalatest.ScalatraSuite

class SparkRulesServletTestSuite extends FlatSpecLike with ScalatraSuite {

    System.setProperty( "kie.maven.settings.custom", System.getProperty( "user.dir" ) + "/conf/" + "settings.xml" )

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
        System.out.println( "From the test, the settings.xml file is : " + System.getProperty( "kie.maven.settings.custom" ) )
        val input = "[{\"value\": \"Michael\"},{\"value\": \"Michael\"},{\"value\": \"Michael\"}]"
        val expectedOutput = "{\"value\":\"Michael\",\"count\":3}"

        //@formatter:off
        post( "/execute", input.getBytes, Map( "Content-Type" -> "application/json" ) ) {
            status should equal ( 201 )
            Thread.sleep( 1000 )
//            body should equal ( expectedOutput )
        }
        //@formatter:on
    }

}