package com.codefirst.bdd.gherkinatorbdd.v2

import com.codefirst.bdd.gherkinatorbdd.GherkinatorRobot
import com.codefirst.bdd.gherkinatorbdd.v2.helper.FeatureFilePathHelper

abstract class GherkinatorEngine {

    private val featureFilePathHelper = FeatureFilePathHelper()

    abstract var robots: MutableList<GherkinatorRobot>?

    abstract fun featureFileLocation(): String

    init {
        featureFilePathHelper.checkIfEmpty(this.featureFileLocation())
        featureFilePathHelper.addFeatureFolderPrefixIfNotPresent(this.featureFileLocation())
    }
}