package com.codefirst.bdd.gherkinatorannotation

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class Scenario(val name: String)