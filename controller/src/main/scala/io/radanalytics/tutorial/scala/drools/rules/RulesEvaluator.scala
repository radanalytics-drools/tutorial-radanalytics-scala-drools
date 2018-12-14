package io.radanalytics.tutorial.scala.drools.rules

import org.kie.api.KieBase
import org.kie.api.runtime.ClassObjectFilter

import scala.collection.mutable.ListBuffer

object RulesEvaluator {

    def executeRules( fact : Any, resultClass : Class[ _ ], kbase : KieBase ) : List[ Any ] = {
        val session = kbase.newKieSession()
        session.insert( fact )
        session.fireAllRules

        val resultsBuffer = new ListBuffer[ Any ]()
        val iterator = session.getObjects( new ClassObjectFilter( resultClass ) ).iterator()

        while ( iterator.hasNext ) resultsBuffer += iterator.next

        session.dispose()
        resultsBuffer.toList
    }

}