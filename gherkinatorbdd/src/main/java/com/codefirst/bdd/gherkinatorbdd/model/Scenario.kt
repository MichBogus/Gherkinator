package com.codefirst.bdd.gherkinatorbdd.model

data class Scenario(val name: String,
                    var steps: List<Step>)