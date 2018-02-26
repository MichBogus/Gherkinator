package com.mbogus.gherkinatorbdd

import android.database.sqlite.SQLiteDatabase
import com.mbogus.gherkinatorbdd.model.And
import com.mbogus.gherkinatorbdd.model.Given

class TestRobot(): GherkinatorRobot() {

    @Given("step one")
    fun testStep() {
        val test = ""
    }

    @And("step one")
    fun teststeeep() {

    }

    @Given("Open *\\s* drugstore list")
    fun testRobotWithParam(nameOfDrugstore: String) {

    }
}