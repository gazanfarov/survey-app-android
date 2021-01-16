package com.surveyapp.domain.repository

import com.surveyapp.entity.AnswerEntity
import com.surveyapp.exception.Failure
import com.surveyapp.utils.Either

interface AnswerRepository {

    suspend fun loadQuestionAnswers(questionId: Long): Either<Failure, List<AnswerEntity>>
}