package io.radanalytics.tutorial.scala.drools.service

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener

object Main {
    def main( args : Array[ String ] ) : Unit = {
        val port = 8080
        val server = new Server( port )
        val context = new WebAppContext()

        context.setContextPath( "/" )
        context.setResourceBase( "src/main/webapp" )
        context.setInitParameter( ScalatraListener.LifeCycleKey, "io.radanalytics.tutorial.scala.drools.service.ScalatraInit" ) // scalatra uses some magic defaults I don't like
        context.addEventListener( new ScalatraListener )

        server.setHandler( context )
        server.start()
        server.join()
    }
}