package com.codefirst.bdd.gherkinatorbdd.v2.scenarioprovider

import android.content.Context
import com.codefirst.bdd.gherkinatorbdd.v2.FEATURE_FILE_SEGMENT
import com.codefirst.bdd.gherkinatorbdd.v2.FEATURE_SEGMENT_MISSNG
import com.codefirst.bdd.gherkinatorbdd.v2.exceptions.GherkinatorNoFeatureSegmentException
import java.lang.StringBuilder

class ScenarioProvider {

    private val fileReader = FileReader()

    fun readScenarios(context: Context, fileName: String) {
        val wholeFile = fileReader.read(context, fileName)

        checkIfFileContainsFeatureSegment(wholeFile)

    }

    private fun checkIfFileContainsFeatureSegment(wholeFile: StringBuilder) {
        if (wholeFile.contains(FEATURE_FILE_SEGMENT, true).not())
            throw GherkinatorNoFeatureSegmentException(FEATURE_SEGMENT_MISSNG)
    }
}