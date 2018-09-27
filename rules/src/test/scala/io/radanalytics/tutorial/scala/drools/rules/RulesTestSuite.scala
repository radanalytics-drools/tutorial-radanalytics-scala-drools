package io.radanalytics.tutorial.scala.drools.rules

import io.radanalytics.tutorial.scala.drools.model.{Input, Output}
import org.drools.core.ClassObjectFilter
import org.kie.api.KieServices
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.JavaConverters._

class RulesTestSuite extends FlatSpec with Matchers {

    "The rules" should "product the correct Output for given Input" in {
        val input1 = Input( "Michael" )
        val input2 = Input( "Michael" )

        val expectedOutput = Output( "Michael", 2 )

        val session = KieServices.Factory.get().newKieClasspathContainer().newKieSession( "my-ksession" )

        session.insert( input1 )
        session.insert( input2 )

        val firedCount = session.fireAllRules()

        firedCount should be( 1 )
        session.getObjects( new ClassObjectFilter( classOf[ Output ] ) ).asScala.toSet.contains( expectedOutput ) should be( true )
    }
}
