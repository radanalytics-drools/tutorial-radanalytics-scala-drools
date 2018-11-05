package io.radanalytics.tutorial.scala.drools.service


import java.util.Properties

import io.radanalytics.tutorial.scala.drools.model.{Input, Output}
import io.radanalytics.tutorial.scala.drools.rules.RulesProvider
import org.json4s.{DefaultFormats, Formats}
import org.kie.api.runtime.{ClassObjectFilter, KieContainer}
import org.scalatra.json._
import org.scalatra.{Created, Forbidden, Ok, ScalatraServlet}
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.Source
import scala.util.{Failure, Success}

class SparkRulesServlet extends ScalatraServlet with RulesProvider with JacksonJsonSupport {

    val LOG : Logger = LoggerFactory.getLogger( classOf[ ScalatraServlet ] )
    var output : Option[ Output ] = None

    protected implicit lazy val jsonFormats : Formats = DefaultFormats.withBigDecimal

    //TODO - @michael - find a way to work around the API to make this a val
    var container : KieContainer = {
        val props = {
            val props : Properties = new Properties()
            props.load( Source.fromURL( getClass.getResource( "/startup-rules.properties" ) ).bufferedReader )
            props
        }
        loadRules( props )
    }

    def reloadRules( group : String, artifact : String, version : String ) : Unit = this.container = loadDynamicRules( group, artifact, version )

    //=====================================
    //  Scalatra Route code starts here
    //=====================================

    //@formatter:off
    before() {
        contentType = formats( "json" )
    }

    get( "/") {
        Ok( "The Spark + Drools application is up and running!" )
    }

    put( "/reload" ) {
        val group : Option[String] = params.get( "group" )
        val artifact : Option[String] = params.get( "artifact" )
        val version : Option[String] = params.get( "version" )

        if( group.isEmpty ) Forbidden( "Group is required" )
        if( artifact.isEmpty )  Forbidden( "Artifact is required" )
        if( version.isEmpty ) Forbidden( "Version is required" )

        reloadRules( group.get, artifact.get, version.get )
        Ok( "{\"message\":\"Rules successfully reloaded\"}" ) //TODO - define a proper JSON interface
    }

    post( "/execute" )  {
        val input : List[Input] = parsedBody.extract[ List[ Input ] ]

        val process = Future {
            val session = container.newKieSession( "my-ksession" )
            input.foreach( i => session.insert( i ) )
            session.fireAllRules
            output  = Some( session.getObjects( new ClassObjectFilter( classOf[ Output ] ) ).stream().findFirst().get().asInstanceOf[ Output ] )

        }

        process.onComplete {
            case Success( value ) => println( s"Success!!! $output" )
            case Failure( e ) => e.printStackTrace
        }

        Created( "Spark rules process was started" )
    }
    //@formatter:on
}