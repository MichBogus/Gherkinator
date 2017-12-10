package com.mbogus.gherkinatorbdd

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.mbogus.gherkinatorbdd.model.ScenarioTest
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
    @ScenarioTest("This is first test")
    fun thisIsTestRun() {
        runScenario()
    }
}