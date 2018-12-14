package io.radanalytics.tutorial.scala.drools.rules

import org.kie.api.KieServices
import org.kie.api.builder.{KieScanner, ReleaseId}
import org.kie.api.runtime.KieContainer

trait RulesProvider {

    def loadDynamicRules( group : String, artifact : String, version : String ) : KieContainer = {
        val releaseId : ReleaseId = KieServices.Factory.get.newReleaseId( group, artifact, version )
        val newContainer : KieContainer = KieServices.Factory.get.newKieContainer( releaseId )

        val scanner : KieScanner = KieServices.Factory.get.newKieScanner( newContainer )
        scanner.scanNow()
        scanner.stop()

        newContainer
    }

    def loadStaticRules( ) : KieContainer = KieServices.Factory.get.newKieClasspathContainer

}
