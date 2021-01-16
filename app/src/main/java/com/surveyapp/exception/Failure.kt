package com.surveyapp.exception

sealed class Failure {
    object NetworkConnection : Failure()

    object ServerError : Failure()

    object UnknownError : Failure()

    open val throwable: Throwable
        get() = Throwable("Failure: $this")

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()

}