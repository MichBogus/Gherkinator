package com.codefirst.bdd.gherkinatorbdd.v2

import android.content.Context
import com.codefirst.bdd.gherkinatorbdd.GherkinatorRobot
import com.codefirst.bdd.gherkinatorbdd.v2.helper.FeatureFilePathHelper
import com.codefirst.bdd.gherkinatorbdd.v2.scenarioprovider.FileReader
import com.codefirst.bdd.gherkinatorbdd.v2.scenarioprovider.ScenarioProvider

abstract class GherkinatorEngine(val context: Context) {

    private val featureFilePathHelper = FeatureFilePathHelper()
    private val featureFileReader = FileReader()
    private val scenarioProvider = ScenarioProvider()

    abstract var robots: MutableList<GherkinatorRobot>?

    abstract fun featureFileLocation(): String

    init {
        featureFilePathHelper.checkIfEmpty(this.featureFileLocation())
        scenarioProvider.readScenarios(context, featureFilePathHelper.addFeatureFolderPrefixIfNotPresent(this.featureFileLocation()))
    }
}