package com.mbogus.gherkinatorbdd.runner

import com.mbogus.gherkinatorbdd.model.ScenarioTest
import java.lang.IllegalStateException

object StackTraceRunner {

    fun getScenarioDefinitionByReflection(): String {
        Thread.currentThread().stackTrace.forEach {
            try {
                val callerClass = Class.forName(it.className)
                val method = callerClass.getMethod(it.methodName)
                val annotation = method.getAnnotation(ScenarioTest::class.java)
                if (annotation != null) {
                    if (annotation.nameOfScenario.isEmpty()) {
                        throw IllegalStateException("GHERKINATOR: You didn't provide run with scenario definition")
                    }
                    return annotation.nameOfScenario
                }
            } catch (ex: Exception) {

            }
        }
        throw IllegalStateException("GHERKINATOR: You didn't provide run with scenario definition")
    }
}