package com.surveyapp.presentation.question

import com.surveyapp.base.BaseMvpView
import com.surveyapp.entity.AnswerEntity
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface HoursQuestionView : BaseMvpView {

    @AddToEndSingle
    fun showQuestion(question: String)

    @Skip
    fun setAnswerItems(
        answers: List<AnswerEntity>,
        chosenAnswerId: Long?
    )

    @Skip
    fun openPaymentQuestion(
        questionId: Long,
        chosenAnswerId: Long
    )
}