package com.codefirst.bdd.gherkinatorbdd.v2.exceptions

import java.lang.RuntimeException

class GherkinatorFeatureFilePathException(val name: String): RuntimeException(name)

class GherkinatorNoFeatureSegmentException(val name: String): RuntimeException(name)