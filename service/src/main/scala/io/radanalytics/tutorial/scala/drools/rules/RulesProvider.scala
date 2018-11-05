package io.radanalytics.tutorial.scala.drools.rules

import java.util.Properties

import org.kie.api.KieServices
import org.kie.api.builder.{KieScanner, ReleaseId}
import org.kie.api.runtime.KieContainer

trait RulesProvider {

    def loadRules( props : Properties ) : KieContainer = {
        val rulePkgType : String = props.getProperty( "rule.deployment.type" ).toUpperCase
        println( "########" + rulePkgType )
        rulePkgType match {
            case "" => loadStaticRules()
            case "STATIC" => loadStaticRules()
            case "DYNAMIC" => {
                val group = props.getProperty( "startup.rules.group" )
                val artifact = props.getProperty( "startup.rules.artifact" )
                val version = props.getProperty( "startup.rules.version" )
                loadDynamicRules( group, artifact, version )
            }
            case _ => throw new IllegalArgumentException( "" )
        }
    }

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
