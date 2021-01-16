package com.surveyapp.data.repository

import com.surveyapp.domain.repository.QuestionRepository
import com.surveyapp.entity.QuestionEntity
import com.surveyapp.exception.Failure
import com.surveyapp.utils.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuestionRepositoryImpl : QuestionRepository {

    private val questions = listOf(
        QuestionEntity(
            id = 1,
            value = "How many hours did you have electricity yesterday?"
        ),
        QuestionEntity(
            id = 2,
            value = "What total did you pay for your electricity last week?"
        )
    )

    override suspend fun loadQuestions(): Either<Failure, List<QuestionEntity>> {
        return withContext(Dispatchers.IO) {
            Either.Right(questions)
        }
    }
}