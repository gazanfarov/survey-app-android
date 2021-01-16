package com.surveyapp.presentation.intro

import com.surveyapp.base.BaseMvpPresenter
import com.surveyapp.domain.interactor.SurveyInteractor
import com.surveyapp.utils.Either
import kotlinx.coroutines.launch
import moxy.InjectViewState

@InjectViewState
class IntroPresenter(
    private val hoursSavedAnswerId: Long,
    private val paymentSavedAnswerId: Long,
    private val surveyInteractor: SurveyInteractor
) : BaseMvpPresenter<IntroView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        checkSavedAnswers()
    }

    override fun attachView(view: IntroView?) {
        super.attachView(view)
        viewState.hideKeyboard()
    }

    fun startSurveyBtnClicked() {
        launch {
            val firstQuestionId =
                (surveyInteractor.loadQuestions() as? Either.Right)?.b?.get(0)?.id ?: 0
            viewState.openHoursQuestion(firstQuestionId)
        }
    }

    private fun checkSavedAnswers() {
        if (hoursSavedAnswerId != DEFAULT_ID && paymentSavedAnswerId != DEFAULT_ID) {
            viewState.showSavedSurveySnackBar()
        }
    }

    fun backButtonPressed() {
        viewState.closeSurveyFlow()
    }

    fun removedSavedQuestionsBtnClicked() {
        launch {
            surveyInteractor.deleteQuestionAnswer(hoursSavedAnswerId)
            surveyInteractor.deleteQuestionAnswer(paymentSavedAnswerId)
            viewState.returnBackToSurveyQuestions()
        }
    }

    companion object {
        private const val DEFAULT_ID = 0L
    }
}