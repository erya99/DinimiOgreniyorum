package com.dinimiogreniyorum.app

import com.dinimiogreniyorum.db.AppDatabase
import com.dinimiogreniyorum.db.Question
import com.dinimiogreniyorum.db.SelectProgressByCategory

class QuestionRepository(private val database: AppDatabase) {

    private val queries = database.appDatabaseQueries

    fun getQuestionsByCategory(category: String): List<Question> {
        return queries.selectAllByCategory(category).executeAsList()
    }

    fun getQuestionsByLevel(category: String, level: String): List<Question> {
        return queries.selectByLevel(category, level).executeAsList()
    }

    fun insertQuestion(
        category: String,
        level: String,
        questionText: String,
        correctAnswer: String,
        options: String,
        explanation: String
    ) {
        queries.insertQuestion(category, level, questionText, correctAnswer, options, explanation)
    }

    fun updateProgress(questionId: Long, attempts: Long, correctCount: Long) {
        queries.updateProgress(
            questionId = questionId,
            attempts = attempts,
            correctCount = correctCount,
            lastSeen = kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
        )
    }

    fun getAllProgress() = queries.selectAllProgress().executeAsList()

    fun getTotalQuestions(): Long {
        return queries.selectTotalQuestions().executeAsOne()
    }

    fun getTotalAttempts(): Long {
        return queries.selectTotalAttempts().executeAsOne() ?: 0L
    }

    fun getTotalCorrect(): Long {
        return queries.selectTotalCorrect().executeAsOne() ?: 0L
    }

    fun getProgressByCategory(): List<SelectProgressByCategory> {
        return queries.selectProgressByCategory().executeAsList()
    }
}