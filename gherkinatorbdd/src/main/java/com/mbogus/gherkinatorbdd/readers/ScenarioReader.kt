package com.mbogus.gherkinatorbdd.readers

import android.content.res.AssetManager
import com.mbogus.gherkinatorbdd.model.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder

class ScenarioReader(val assetManager: AssetManager,
                     val fileName: String) {

    private val NEW_LINE_MARK = "GHER!"
    private val FEATURE = "Feature:"
    private val SCENARIO = "Scenario:"
    private val GIVEN = "Given:"
    private val WHEN = "When:"
    private val THEN = "Then:"
    private val AND = "And:"

    private val NAME_OF_FEATURE_INDEX = 0
    private val NAME_OF_SCENARIO_INDEX = 0

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
            return gatherScenarios(wholeFile)
        }

        return emptyList()
    }

    private fun gatherScenarios(wholeFile: StringBuilder): MutableList<Scenario> {
        val scenarios = wholeFile.split(SCENARIO)

        val featureName = scenarios[NAME_OF_FEATURE_INDEX]

        val listOfScenarios = mutableListOf<Scenario>()
        scenarios
                .filterNot { it.contains(featureName) }
                .map { it.split("\n") }
                .forEach {
                    val listOfSteps = mutableListOf<Step>()
                    val scenario = Scenario(it[NAME_OF_SCENARIO_INDEX].trim(), emptyList())
                    for (step: String in it) {
                        with(gatherStep(step)) {
                            this?.let {
                                listOfSteps.add(this)
                            }
                        }
                    }
                    scenario.steps = listOfSteps
                    listOfScenarios.add(scenario)
                }
        return listOfScenarios
    }

    private fun gatherStep(step: String): Step? =
            when {
                step.contains(GIVEN) -> Step(step.split(GIVEN)[1].trim(), Given::class.toString())
                step.contains(WHEN) -> Step(step.split(WHEN)[1].trim(), When::class.toString())
                step.contains(THEN) -> Step(step.split(THEN)[1].trim(), Then::class.toString())
                step.contains(AND) -> Step(step.split(AND)[1].trim(), And::class.toString())
                else -> null
            }
}
