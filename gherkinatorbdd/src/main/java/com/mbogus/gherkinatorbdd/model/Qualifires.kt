package com.mbogus.gherkinatorbdd.model

annotation class ScenarioTest(val nameOfScenario: String)

annotation class Given(val definiton: String)

annotation class When(val definiton: String)

annotation class Then(val definiton: String)

annotation class And(val definiton: String)