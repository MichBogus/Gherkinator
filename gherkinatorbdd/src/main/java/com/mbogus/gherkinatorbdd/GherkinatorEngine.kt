package com.mbogus.gherkinatorbdd

import android.content.Context
import com.mbogus.gherkinatorbdd.model.Scenario
import com.mbogus.gherkinatorbdd.readers.ScenarioReader
import com.mbogus.gherkinatorbdd.runner.StackTraceRunner
import com.mbogus.gherkinatorbdd.runner.StepRunner
import java.lang.IllegalStateException

abstract class GherkinatorEngine(context: Context) {

    private var listOfScenarios: List<Scenario>
    lateinit var robots: MutableList<GherkinatorRobot>

    private lateinit var stepsRunner: StepRunner

    abstract fun featureFileLocation(): String

    init {
        if (featureFileLocation().isEmpty()) {
            throw IllegalStateException("GHERKINATOR: You didn't provide any feature file to your test class")
        }
        listOfScenarios = ScenarioReader(context.assets, featureFileLocation()).readScenarios()
    }

    fun runScenario() {
        val scenarioDefinition = StackTraceRunner.getScenarioDefinitionByReflection()

        stepsRunner = StepRunner(robots)

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