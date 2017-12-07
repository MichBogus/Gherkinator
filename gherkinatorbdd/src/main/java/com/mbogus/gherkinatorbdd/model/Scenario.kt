package com.mbogus.gherkinatorbdd.model

data class Scenario(val name: String,
                    val steps: List<Step>)