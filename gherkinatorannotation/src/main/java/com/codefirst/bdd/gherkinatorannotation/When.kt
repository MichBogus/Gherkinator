package com.codefirst.bdd.gherkinatorannotation

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class When(val definiton: String)