package io.radanalytics.tutorial.drools.scala.web.model

import io.radanalytics.tutorial.drools.rule.model.{Input => InputRules, Output => OutputRules}
import io.radanalytics.tutorial.drools.scala.web.model.{Input => InputWeb, Output => OutputWeb}

object ModelMappings {

    implicit def webInputToRulesInput( input : InputWeb ) : InputRules = new InputRules( input.value )

    implicit def webInputListToRulesInputList( input : List[ InputWeb ] ) : List[ InputRules ] = input.map( i => new InputRules( i.value ) )

    implicit def rulesInputToWebInput( input : InputRules ) : InputWeb = InputWeb( input.getVal )

    implicit def rulesInputListToWebInputList( input : List[ InputRules ] ) : List[ InputWeb ] = input.map( i => Input( i.getVal ) )

    implicit def webOutputToRulesOutput( output : OutputWeb ) : OutputRules = new OutputRules( output.count )

    implicit def rulesOutputToWebOutput( output : OutputRules ) : OutputWeb = OutputWeb( output.getCount )
}