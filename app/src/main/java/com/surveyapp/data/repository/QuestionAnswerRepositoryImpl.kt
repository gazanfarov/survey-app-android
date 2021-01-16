package com.surveyapp.data.repository

import com.surveyapp.domain.repository.QuestionAnswerRepository
import com.surveyapp.entity.AnswerEntity
import com.surveyapp.entity.QuestionAnswerEntity
import com.surveyapp.entity.QuestionEntity
import com.surveyapp.exception.Failure
import com.surveyapp.utils.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class QuestionAnswerRepositoryImpl : QuestionAnswerRepository {

    private val questionAnswers = mutableListOf<QuestionAnswerEntity>()

    override suspend fun saveQuestionAnswer(
        question: QuestionEntity,
        answer: AnswerEntity
    ): Either<Failure, Long> {
        return withContext(Dispatchers.IO) {
            val id = Random.nextLong(1, 1000)
            questionAnswers.add(
                QuestionAnswerEntity(
                    id = id,
                    questionEntity = question,
                    answerEntity = answer
                )
            )
            Either.Right(id)
        }
    }

    override suspend fun deleteQuestionAnswer(questionAnswerId: Long): Either<Failure, Boolean> {
        return withContext(Dispatchers.IO) {
            questionAnswers.removeAt(
                questionAnswers.indexOf(
                    questionAnswers.first { it.id == questionAnswerId }
                )
            )
            Either.Right(true)
        }
    }
}