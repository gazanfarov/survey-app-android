package com.surveyapp.domain.repository

import com.surveyapp.entity.AnswerEntity
import com.surveyapp.entity.QuestionEntity
import com.surveyapp.exception.Failure
import com.surveyapp.utils.Either

interface QuestionAnswerRepository {

    suspend fun saveQuestionAnswer(
        question: QuestionEntity,
        answer: AnswerEntity
    ): Either<Failure, Long>

    suspend fun deleteQuestionAnswer(questionAnswerId: Long): Either<Failure, Boolean>
}