package com.codefirst.bdd.gherkinatorbdd.v2.scenarioprovider

import android.content.Context

class ScenarioProvider {

    val fileReader = FileReader()

    fun readScenarios(context: Context, fileName: String) {
        val wholeFile = fileReader.read(context, fileName)
    }
}