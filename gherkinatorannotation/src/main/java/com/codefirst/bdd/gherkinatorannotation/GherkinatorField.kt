package com.codefirst.bdd.gherkinatorannotation

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FIELD)
annotation class GherkinatorField(val fieldName: String)