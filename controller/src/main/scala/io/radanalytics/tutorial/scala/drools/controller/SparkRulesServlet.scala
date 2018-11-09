package io.radanalytics.tutorial.scala.drools.controller


import java.util.Properties

import io.radanalytics.tutorial.drools.rule.model.{Input => InputRules, Output => OutputRules}
import io.radanalytics.tutorial.drools.scala.web.model.{Job, Input => InputWeb, Output => OutputWeb}
import io.radanalytics.tutorial.scala.drools.rules.{RulesEvaluator, RulesProvider}
import org.json4s.{DefaultFormats, Formats}
import org.kie.api.runtime.{ClassObjectFilter, KieContainer}
import org.scalatra.json._
import org.scalatra._
import org.slf4j.{Logger, LoggerFactory}
import io.radanalytics.tutorial.drools.scala.web.model.ImplicitModelMappings._
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.{SparkConf, SparkContext}
import org.kie.api.KieBase

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.Source
import scala.util.{Failure, Success}

class SparkRulesServlet extends ScalatraServlet with RulesProvider with JacksonJsonSupport {

    protected implicit lazy val jsonFormats : Formats = DefaultFormats.withBigDecimal
    val LOG : Logger = LoggerFactory.getLogger( classOf[ ScalatraServlet ] )
    val spark : SparkContext = new SparkContext( new SparkConf().setAppName( "Radanalytics IO SparkPI Scalatra Tutorial" ) )

    val m = Map( 1 -> "one", 2 -> "two" )
    m.foreach( println )
    m.map( x => x._1 )

    var output : Option[ OutputRules ] = None
    var jobCount : Int = 0
    //TODO - @michael - find a way to work around the API to make this a val
    var container : KieContainer = {
        val props = {
            LOG.info( "Using custom settings.xml file : " + System.getProperty( "kie.maven.settings.custom" ) )
            val props : Properties = new Properties()
            props.load( Source.fromURL( getClass.getResource( "/startup-rules.properties" ) ).bufferedReader )
            props
        }
        loadDynamicRules( props.getProperty( "startup.rules.group" ), props.getProperty( "startup.rules.artifact" ), props.getProperty( "startup.rules.version" ) )
    }

    //=====================================
    // Scalatra Routes
    //=====================================

    //@formatter:off
    before() {
        contentType = formats( "json" )
    }

    get( "/") {
        Ok( "The Spark + Drools application is up and running!" )
    }

    get( "/output" ){
         if( !output.isEmpty ) {
             val o : OutputWeb = output.get
             Ok( o )
         }
        else{
             NotFound( "The process has not yet completed" )
         }
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
        val spark : SparkContext = new SparkContext( new SparkConf().setAppName( "Radanalytics IO SparkPI Scalatra Tutorial" ) )

        val requestInput : List[ InputRules ] = parsedBody.extract[ List[ InputWeb ] ] //~~NOTE~~ - InputWeb is implicitly converted to InputRules
        val input = spark.makeRDD[ InputRules ]( requestInput )

        val kbase : Broadcast[ KieBase ] = spark.broadcast( this.container.getKieBase )

        val process = Future {
//            spark.parallelize( input ).map( i => RulesEvaluator.executeRules(i, classOf[InputRules], kbase.value ) ).filter( i => )
        }

        process.onComplete {
            case Success( value ) => LOG.info( "Success : " + output.get )
            case Failure( e ) => e.printStackTrace
        }

        Created( Job( s"$jobCount", "Spark + Drools job was started" ) )
    }
    //@formatter:on


    //=====================================
    // Support code
    //=====================================
    def reloadRules( group : String, artifact : String, version : String ) : Unit = this.container = loadDynamicRules( group, artifact, version )
}