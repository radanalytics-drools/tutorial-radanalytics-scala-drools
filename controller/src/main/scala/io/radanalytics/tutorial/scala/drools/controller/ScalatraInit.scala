package io.radanalytics.tutorial.scala.drools.controller

import javax.servlet.ServletContext
import org.scalatra.LifeCycle

class ScalatraInit extends LifeCycle {
    override def init( context : ServletContext ) {
        context.mount( classOf[ SparkRulesServlet ], "/*" )
    }
}
