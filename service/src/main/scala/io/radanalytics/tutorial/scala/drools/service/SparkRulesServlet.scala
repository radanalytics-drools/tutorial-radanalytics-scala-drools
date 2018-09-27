package io.radanalytics.tutorial.scala.drools.service

import org.scalatra.{Created, Ok, ScalatraServlet}

class SparkRulesServlet extends ScalatraServlet {

    //@formatter:off
    get( "/" ) {
        Ok( "The Spark + Drools application is up and running!" )
    }

    post( "/execute" ) {
        Created( "Executing your Spark + Drools job" )
    }
    //@formatter:on
}
