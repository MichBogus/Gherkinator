package com.codefirst.bdd.gherkinatorbdd.v2.helper

import com.codefirst.bdd.gherkinatorbdd.v2.FEATURES_LOCATION
import com.codefirst.bdd.gherkinatorbdd.v2.FEATURE_FILE_PATH_EMPTY
import com.codefirst.bdd.gherkinatorbdd.v2.exceptions.GherkinatorFeatureFilePathException

class FeatureFilePathHelper {

    fun checkIfEmpty(path: String) {
        if (path.isEmpty()) {
            throw GherkinatorFeatureFilePathException(FEATURE_FILE_PATH_EMPTY)
        }
    }

    fun addFeatureFolderPrefixIfNotPresent(path: String): String =
            if (path.contains(FEATURES_LOCATION).not()) {
                FEATURES_LOCATION + path
            } else {
                path
            }
}