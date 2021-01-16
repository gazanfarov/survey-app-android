package com.surveyapp.presentation.payment

import com.surveyapp.base.BaseMvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface PaymentQuestionView : BaseMvpView {

    @AddToEndSingle
    fun showQuestion(question: String)

    @AddToEndSingle
    fun setSubmitBtnAlpha(alpha: Float)

    @Skip
    fun openIntro(
        hoursAnswerId: Long,
        paymentAnswerId: Long
    )
}