package com.mbogus.gherkinatorbdd

import android.content.Context
import com.mbogus.gherkinatorbdd.model.*
import com.mbogus.gherkinatorbdd.readers.ScenarioReader
import java.lang.IllegalStateException

abstract class GherkinatorEngine(val context: Context) {

    lateinit var listOfScenarios: List<Scenario>
    open lateinit var robots: MutableList<GherkinatorRobot>

    open lateinit var scenarioDefinition: String

    abstract fun featureFileLocation(): String

    init {
        if (featureFileLocation().isEmpty()) {
            throw IllegalStateException("GHERKINATOR: You didn't provide any feature file to your test class")
        }
        listOfScenarios = ScenarioReader(context.assets, featureFileLocation()).readScenarios()
    }

    fun run() {
        if (scenarioDefinition.isEmpty()) {
            throw IllegalStateException("GHERKINATOR: You didn't provide run with scenario definition")
        }

        listOfScenarios.filter { it.name == scenarioDefinition }
                .forEach {
                    it.steps.forEach {
                        runStepInRobots(it)
                    }
                }
    }

    private fun runStepInRobots(step: Step) {
        when {
            step.qualifier == Given::class.toString() -> {
                runStepForGivenAnnotation(step.definition)
            }
            step.qualifier == When::class.toString() -> {
                runStepForWhenAnnotation(step.definition)
            }
            step.qualifier == Then::class.toString() -> {
                runStepForThenAnnotation(step.definition)
            }
            step.qualifier == And::class.toString() -> {
                runStepForAndAnnotation(step.definition)
            }
        }
    }

    private fun runStepForAndAnnotation(definition: String) {
        robots.forEach {
            it.javaClass.methods
                    .filter { it.getAnnotation(And::class.java) != null }
                    .forEach { method ->
                        val definedAnnotation = method.getAnnotation(And::class.java)
                        if (definedAnnotation.definiton == definition) {
                            method.invoke(it)
                            return
                        }
                    }
            throw IllegalStateException("GHERKINATOR: There is no \"$definition\" step definition in your robots")
        }
    }

    private fun runStepForThenAnnotation(definition: String) {
        robots.forEach {
            it.javaClass.methods
                    .filter { it.getAnnotation(Then::class.java) != null }
                    .forEach { method ->
                        val definedAnnotation = method.getAnnotation(Then::class.java)
                        if (definedAnnotation.definiton == definition) {
                            method.invoke(it)
                            return
                        }
                    }
            throw IllegalStateException("GHERKINATOR: There is no \"$definition\" step definition in your robots")
        }
    }

    private fun runStepForWhenAnnotation(definition: String) {
        robots.forEach {
            it.javaClass.methods
                    .filter { it.getAnnotation(When::class.java) != null }
                    .forEach { method ->
                        val definedAnnotation = method.getAnnotation(When::class.java)
                        if (definedAnnotation.definiton == definition) {
                            method.invoke(it)
                            return
                        }
                    }
            throw IllegalStateException("GHERKINATOR: There is no \"$definition\" step definition in your robots")
        }
    }

    private fun runStepForGivenAnnotation(definition: String) {
        robots.forEach {
            it.javaClass.methods
                    .filter { it.getAnnotation(Given::class.java) != null }
                    .forEach { method ->
                        val definedAnnotation = method.getAnnotation(Given::class.java)
                        if (definedAnnotation.definiton == definition) {
                            method.invoke(it)
                            return
                        }
                    }
            throw IllegalStateException("GHERKINATOR: There is no \"$definition\" step definition in your robots")
        }
    }
}