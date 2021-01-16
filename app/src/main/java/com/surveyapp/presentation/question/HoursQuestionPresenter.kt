package com.surveyapp.presentation.question

import com.surveyapp.base.BaseMvpPresenter
import com.surveyapp.domain.interactor.SurveyInteractor
import com.surveyapp.entity.AnswerEntity
import com.surveyapp.entity.QuestionEntity
import com.surveyapp.utils.Either
import kotlinx.coroutines.launch
import moxy.InjectViewState
import kotlin.random.Random

@InjectViewState
class HoursQuestionPresenter(
    private val questionId: Long,
    private val surveyInteractor: SurveyInteractor
) : BaseMvpPresenter<HoursQuestionView>() {

    private var currentQuestion: QuestionEntity? = null
    private var currentQuestionAnswers = mutableListOf<AnswerEntity>()
    private var chosenAnswerId: Long? = null

    override fun attachView(view: HoursQuestionView?) {
        super.attachView(view)
        viewState.hideKeyboard()
        loadQuestion()
        loadQuestionAnswers()
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

    private fun loadQuestionAnswers() {
        launch {
            when (val result = surveyInteractor.loadQuestionAnswers(questionId)) {
                is Either.Left -> {
                    // No error handling
                }
                is Either.Right -> {
                    currentQuestionAnswers.clear()
                    currentQuestionAnswers.addAll(result.b)
                    viewState.setAnswerItems(
                        answers = currentQuestionAnswers,
                        chosenAnswerId = chosenAnswerId
                    )
                }
            }
        }
    }

    fun onAnswerChosen(answerId: Long) {
        launch {
            chosenAnswerId = answerId
            val nextQuestionId =
                (surveyInteractor.loadQuestions() as? Either.Right)?.b?.get(1)?.id ?: 1
            viewState.openPaymentQuestion(
                questionId = nextQuestionId,
                chosenAnswerId = answerId
            )
        }
    }
}