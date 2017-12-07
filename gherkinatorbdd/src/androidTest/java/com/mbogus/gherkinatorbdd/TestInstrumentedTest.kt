package com.mbogus.gherkinatorbdd

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestInstrumentedTest : GherkinatorEngine(InstrumentationRegistry.getTargetContext()) {

    override fun featureFileLocation(): String = "features/test.feature"

    @Before
    fun setUp() {
        robots = mutableListOf(TestRobot())
    }

    @Test
    fun thisIsTestRun() {
        scenarioDefinition = "This is first test"
        run()

        val appContext = InstrumentationRegistry.getTargetContext()

        assertEquals("com.mbogus.gherkinatorbdd.test", appContext.packageName)
    }
}