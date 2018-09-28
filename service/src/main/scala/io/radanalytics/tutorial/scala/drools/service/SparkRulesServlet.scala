package io.radanalytics.tutorial.scala.drools.service

import io.radanalytics.tutorial.scala.drools.model.{Input, Output}
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import org.scalatra.{Ok, ScalatraServlet}

class SparkRulesServlet extends ScalatraServlet with JacksonJsonSupport {

    protected implicit lazy val jsonFormats : Formats = DefaultFormats.withBigDecimal

    //@formatter:off
    before() {
        contentType = formats( "json" )
    }

    get( "/") {
        Ok( "The Spark + Drools application is up and running!" )
    }

    post( "/execute" )  {
        val input : List[Input] = parsedBody.extract[List[Input]]
        Ok( Output( "Michael", 3 ) )
    }
    //@formatter:on
}
