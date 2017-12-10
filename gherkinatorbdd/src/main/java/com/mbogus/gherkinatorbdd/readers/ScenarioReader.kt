package com.mbogus.gherkinatorbdd.readers

import android.content.res.AssetManager
import com.mbogus.gherkinatorbdd.model.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder

class ScenarioReader(val assetManager: AssetManager,
                     val fileName: String) {

    private val FEATURE = "Feature:"
    private val SCENARIO = "Scenario:"
    private val BACKGROUND = "Background:"
    private val GIVEN = "Given:"
    private val WHEN = "When:"
    private val THEN = "Then:"
    private val AND = "And:"

    private val NAME_OF_FEATURE_INDEX = 0
    private val NAME_OF_SCENARIO_INDEX = 0
    private val NAME_OF_STEP_INDEX = 1

    fun readScenarios(): List<Scenario> {
        val reader = BufferedReader(InputStreamReader(assetManager.open(fileName)))

        val wholeFile = StringBuilder()
        var fileLine: String? = reader.readLine()
        while (fileLine != null) {
            if (fileLine.isNotEmpty()) {
                wholeFile.append("${fileLine.trim()}\n")
            }

            fileLine = reader.readLine()
        }
        reader.close()

        if (wholeFile.isNotEmpty()) {
            checkIfFileContainsFeatureSegment(wholeFile)
            return gatherScenarios(wholeFile)
        }

        return emptyList()
    }

    private fun checkIfFileContainsFeatureSegment(wholeFile: StringBuilder) {
        if (wholeFile.contains(FEATURE, true).not())
            throw IllegalStateException("GHERKINATOR: This file does not contain \"Feature:\" segment")
    }

    private fun gatherScenarios(wholeFile: StringBuilder): MutableList<Scenario> {
        val scenarios = wholeFile.split(SCENARIO)

        val backgroundSteps = gatherBackgroundStepsIfOccur(scenarios)

        with(mutableListOf<Scenario>()) {
            scenarios.filterNot { it.contains(scenarios[NAME_OF_FEATURE_INDEX]) }
                    .map { it.split("\n") }
                    .forEach {
                        val listOfSteps = mutableListOf<Step>()

                        if (backgroundSteps.isNotEmpty()) {
                            listOfSteps += backgroundSteps
                        }

                        val scenario = Scenario(it[NAME_OF_SCENARIO_INDEX].trim(), emptyList())
                        for (step: String in it) {
                            with(gatherStep(step)) {
                                this?.let {
                                    listOfSteps.add(this)
                                }
                            }
                        }
                        scenario.steps = listOfSteps
                        this.add(scenario)
                    }
            return this
        }
    }

    private fun gatherBackgroundStepsIfOccur(scenarios: List<String>): MutableList<Step> {
        val listOfBackgroundSteps = mutableListOf<Step>()
        scenarios.filter { it.contains(BACKGROUND) }
                .flatMap { it.split(BACKGROUND) }
                .flatMap { it.split("\n") }
                .filter { it.startsWith(GIVEN) || it.startsWith(WHEN) || it.startsWith(THEN) || it.startsWith(AND) }
                .forEach { step ->
                    with(gatherStep(step)) {
                        this?.let {
                            listOfBackgroundSteps.add(this)
                        }
                    }
                }
        return listOfBackgroundSteps
    }

    private fun gatherStep(step: String): Step? =
            when {
                step.contains(GIVEN) -> Step(step.split(GIVEN)[NAME_OF_STEP_INDEX].trim(), Given::class.toString())
                step.contains(WHEN) -> Step(step.split(WHEN)[NAME_OF_STEP_INDEX].trim(), When::class.toString())
                step.contains(THEN) -> Step(step.split(THEN)[NAME_OF_STEP_INDEX].trim(), Then::class.toString())
                step.contains(AND) -> Step(step.split(AND)[NAME_OF_STEP_INDEX].trim(), And::class.toString())
                else -> null
            }
}
