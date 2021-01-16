package com.surveyapp.di

import com.surveyapp.data.repository.AnswerRepositoryImpl
import com.surveyapp.data.repository.QuestionAnswerRepositoryImpl
import com.surveyapp.data.repository.QuestionRepositoryImpl
import com.surveyapp.domain.interactor.SurveyInteractor
import com.surveyapp.domain.repository.AnswerRepository
import com.surveyapp.domain.repository.QuestionRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoriesModule: Module = module {
    single { AnswerRepositoryImpl() }
    single { QuestionRepositoryImpl() }
    single { QuestionAnswerRepositoryImpl() }
}

val interactorsModule: Module = module {
    single {
        SurveyInteractor(
            questionRepository = get() as QuestionRepositoryImpl,
            answerRepository = get() as AnswerRepositoryImpl,
            questionAnswerRepository = get() as QuestionAnswerRepositoryImpl
        )
    }
}