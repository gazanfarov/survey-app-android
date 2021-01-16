package com.surveyapp.domain.repository

import com.surveyapp.entity.QuestionEntity
import com.surveyapp.exception.Failure
import com.surveyapp.utils.Either

interface QuestionRepository {

    suspend fun loadQuestions(): Either<Failure, List<QuestionEntity>>
}