package com.codefirst.bdd.gherkinatorannotation

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FIELD)
annotation class GherkinatorParam(val paramName: String)