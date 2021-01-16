package com.surveyapp.data.repository

import com.surveyapp.domain.repository.AnswerRepository
import com.surveyapp.entity.AnswerEntity
import com.surveyapp.exception.Failure
import com.surveyapp.utils.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnswerRepositoryImpl : AnswerRepository {

    private val questionPossibleAnswers = hashMapOf(
        Pair(
            1L,
            listOf(
                AnswerEntity(
                    id = 1,
                    value = "0 - 5 hours"
                ),
                AnswerEntity(
                    id = 2,
                    value = "6 - 12 hours"
                ),
                AnswerEntity(
                    id = 3,
                    value = "13 - 18 hours"
                ),
                AnswerEntity(
                    id = 4,
                    value = "19 - 24 hours"
                )
            )
        )
    )

    override suspend fun loadQuestionAnswers(questionId: Long): Either<Failure, List<AnswerEntity>> {
        return withContext(Dispatchers.IO) {
            Either.Right(questionPossibleAnswers[questionId] ?: listOf())
        }
    }
}