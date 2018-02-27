package com.codefirst.bdd.gherkinatorbdd

import android.content.Context
import com.codefirst.bdd.gherkinatorbdd.model.Scenario
import com.codefirst.bdd.gherkinatorbdd.readers.ScenarioReader
import com.codefirst.bdd.gherkinatorbdd.runner.StackTraceRunner
import com.codefirst.bdd.gherkinatorbdd.runner.StepRunner
import java.lang.IllegalStateException

abstract class GherkinatorEngine(context: Context) {

    private val FEATURES_LOCATION = "features/"

    private var listOfScenarios: List<Scenario>
    abstract var robots: MutableList<GherkinatorRobot>?

    private lateinit var stepsRunner: StepRunner

    abstract fun featureFileLocation(): String

    init {
        if (featureFileLocation().isEmpty()) {
            throw IllegalStateException("GHERKINATOR: You didn't provide any feature file to your test class")
        }

        var fileLocation = featureFileLocation()
        if (fileLocation.contains(FEATURES_LOCATION).not()) {
            fileLocation = FEATURES_LOCATION + fileLocation
        }

        listOfScenarios = ScenarioReader(context.assets, fileLocation).readScenarios()
    }

    fun runScenario() {
        if (robots == null || robots?.isEmpty()!!) {
            throw IllegalStateException("GHERKINATOR: You didn't provide any robots")
        }

        stepsRunner = StepRunner(robots!!)

        val scenarioDefinition = StackTraceRunner.getScenarioDefinitionByReflection()
        val scenarioToRun = listOfScenarios.filter { it.name == scenarioDefinition }

        if (scenarioToRun.isEmpty()) {
            throw IllegalStateException("GHERKINATOR: In your feature file there is no such scenario (\"$scenarioDefinition\") to run")
        } else {
            scenarioToRun.forEach {
                it.steps.forEach {
                    stepsRunner.runStepInRobots(it)
                }
            }
        }
    }
}