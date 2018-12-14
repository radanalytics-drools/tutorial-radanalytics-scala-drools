package io.radanalytics.tutorial.scala.drools.controller

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener
import org.slf4j.{Logger, LoggerFactory}

object Main {

    val LOG : Logger = LoggerFactory.getLogger( this.getClass )

    def main( args : Array[ String ] ) : Unit = {
        val port = 8080
        val server = new Server( port )
        val context = new WebAppContext()

        if ( System.getProperty( "kie.maven.settings.custom" ) == null ) {
            if ( args.length == 0 || !args( 0 ).startsWith( "kie.maven.settings.custom" ) ) {
                LOG.warn( "No custom Maven settings file configuration present. The KIE framework will only use Maven defaults" )
            }
            else {
                val settings = args( 0 ).split( "=" )( 1 )
                LOG.info( s"Using Maven configuration $settings" )
                System.setProperty( "kie.maven.settings.custom", settings )
            }
        }

        context.setContextPath( "/" )
        context.setResourceBase( "src/main/webapp" )
        context.setInitParameter( ScalatraListener.LifeCycleKey, "io.radanalytics.tutorial.scala.drools.controller.ScalatraInit" ) // scalatra uses some magic defaults I don't like
        context.addEventListener( new ScalatraListener )

        server.setHandler( context )
        server.start()
        server.join()
    }

}