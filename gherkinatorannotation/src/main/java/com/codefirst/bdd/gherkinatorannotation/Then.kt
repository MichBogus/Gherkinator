package com.codefirst.bdd.gherkinatorannotation

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class Then(val definiton: String)