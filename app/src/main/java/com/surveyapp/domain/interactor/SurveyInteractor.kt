package com.surveyapp.domain.interactor

import com.surveyapp.domain.repository.AnswerRepository
import com.surveyapp.domain.repository.QuestionAnswerRepository
import com.surveyapp.domain.repository.QuestionRepository
import com.surveyapp.entity.AnswerEntity
import com.surveyapp.entity.QuestionEntity
import com.surveyapp.exception.Failure
import com.surveyapp.utils.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SurveyInteractor(
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository,
    private val questionAnswerRepository: QuestionAnswerRepository
) {

    suspend fun loadQuestions(): Either<Failure, List<QuestionEntity>> {
        return withContext(Dispatchers.Default) {
            when (val result = questionRepository.loadQuestions()) {
                is Either.Left -> Either.Left(result.a)
                is Either.Right -> Either.Right(result.b)
            }
        }
    }

    suspend fun loadQuestionAnswers(questionId: Long): Either<Failure, List<AnswerEntity>> {
        return withContext(Dispatchers.Default) {
            when (val result = answerRepository.loadQuestionAnswers(questionId)) {
                is Either.Left -> Either.Left(result.a)
                is Either.Right -> Either.Right(result.b)
            }
        }
    }

    suspend fun saveQuestionAnswer(
        question: QuestionEntity,
        answer: AnswerEntity
    ): Either<Failure, Long> {
        return withContext(Dispatchers.Default) {
            when (val result = questionAnswerRepository.saveQuestionAnswer(
                question = question,
                answer = answer
            )) {
                is Either.Left -> Either.Left(result.a)
                is Either.Right -> Either.Right(result.b)
            }
        }
    }

    suspend fun deleteQuestionAnswer(
        questionAnswerId: Long
    ): Either<Failure, Boolean> {
        return withContext(Dispatchers.Default) {
            when (val result = questionAnswerRepository.deleteQuestionAnswer(questionAnswerId)) {
                is Either.Left -> Either.Left(result.a)
                is Either.Right -> Either.Right(result.b)
            }
        }
    }
}