package com.surveyapp.entity

data class QuestionAnswerEntity(
    val id: Long,
    val questionEntity: QuestionEntity,
    val answerEntity: AnswerEntity
) {
}