package com.surveyapp.presentation.intro

import com.surveyapp.base.BaseMvpView
import moxy.viewstate.strategy.alias.Skip

interface IntroView : BaseMvpView {

    @Skip
    fun openHoursQuestion(questionId: Long)

    @Skip
    fun showSavedSurveySnackBar()

    @Skip
    fun returnBackToSurveyQuestions()

    @Skip
    fun closeSurveyFlow()
}