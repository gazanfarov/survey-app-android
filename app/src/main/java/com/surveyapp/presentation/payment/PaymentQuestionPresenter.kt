package com.surveyapp.presentation.payment

import com.surveyapp.base.BaseMvpPresenter
import com.surveyapp.domain.interactor.SurveyInteractor
import com.surveyapp.entity.AnswerEntity
import com.surveyapp.entity.QuestionEntity
import com.surveyapp.utils.Either
import kotlinx.coroutines.launch
import kotlin.random.Random

class PaymentQuestionPresenter(
    private val questionId: Long,
    private val hoursAnswerId: Long,
    private val surveyInteractor: SurveyInteractor
) : BaseMvpPresenter<PaymentQuestionView>() {

    private var currentQuestion: QuestionEntity? = null
    private var isPaymentMaskFilled: Boolean = false
    private var paymentValue: String = ""

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setSubmitBtnAlpha(NON_ACTIVE_ALPHA_VALUE)
        loadQuestion()
    }

    override fun attachView(view: PaymentQuestionView?) {
        super.attachView(view)
        viewState.showKeyboard()
    }

    private fun loadQuestion() {
        launch {
            when (val result = surveyInteractor.loadQuestions()) {
                is Either.Left -> {
                    // No error handling
                }
                is Either.Right -> {
                    currentQuestion = result.b.first { question ->
                        question.id == questionId
                    }
                    viewState.showQuestion(currentQuestion?.value ?: "")
                }
            }
        }
    }

    fun paymentEditTextFilled(
        maskFilled: Boolean,
        value: String
    ) {
        isPaymentMaskFilled = maskFilled
        paymentValue = value
        if (maskFilled && value != NON_ACCEPTABLE_CURRENCY_VALUE) {
            viewState.setSubmitBtnAlpha(ACTIVE_ALPHA_VALUE)
        } else {
            viewState.setSubmitBtnAlpha(NON_ACTIVE_ALPHA_VALUE)
        }
    }

    fun submitBtnClicked() {
        launch {
            if (isPaymentMaskFilled && paymentValue != NON_ACCEPTABLE_CURRENCY_VALUE) {
                (surveyInteractor.loadQuestions() as? Either.Right)?.b?.get(0)
                    ?.let { hoursQuestion ->
                        (surveyInteractor.loadQuestionAnswers(hoursQuestion.id) as? Either.Right)?.b?.find { hoursAnswer ->
                            hoursAnswer.id == hoursAnswerId
                        }?.let { chosenHoursAnswer ->
                            val hoursSavedAnswerId = (surveyInteractor.saveQuestionAnswer(
                                question = hoursQuestion,
                                answer = chosenHoursAnswer
                            ) as? Either.Right)?.b ?: 0
                            currentQuestion?.let { currentQuestion ->
                                val answer = AnswerEntity(
                                    id = Random.nextLong(1, 1000),
                                    value = paymentValue
                                )
                                val paymentSavedAnswerId = (surveyInteractor.saveQuestionAnswer(
                                    question = currentQuestion,
                                    answer = answer
                                ) as? Either.Right)?.b ?: 0

                                viewState.openIntro(
                                    hoursAnswerId = hoursSavedAnswerId,
                                    paymentAnswerId = paymentSavedAnswerId
                                )
                            }
                        }
                    }
            }
        }
    }

    companion object {
        private const val NON_ACCEPTABLE_CURRENCY_VALUE = "0,00"
        private const val ACTIVE_ALPHA_VALUE = 1f
        private const val NON_ACTIVE_ALPHA_VALUE = 0.3f
    }
}