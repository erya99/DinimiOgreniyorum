package com.dinimiogreniyorum.app

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

data class DailyQuizState(
    val questions: List<com.dinimiogreniyorum.db.Question> = emptyList(),
    val currentIndex: Int = 0,
    val score: Int = 0,
    val isFinished: Boolean = false,
    val selectedAnswer: String? = null,
    val isAnswerRevealed: Boolean = false,
    val isLoading: Boolean = true,
    val isAlreadyCompleted: Boolean = false
)

class DailyViewModel(
    private val repository: QuestionRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    private val _state = MutableStateFlow(DailyQuizState())
    val state: StateFlow<DailyQuizState> = _state

    fun loadDailyQuestions() {
        scope.launch {
            val today = Clock.System.todayIn(TimeZone.currentSystemDefault()).toString()
            val existing = repository.getDailyQuestions(today)

            if (existing.size == 10) {
                _state.value = DailyQuizState(questions = existing, isLoading = false)
            } else {
                // Yeni 10 soru seç
                repository.clearDailySelection()
                val pool = repository.getQuestionsByCategory("DAILY_POOL")
                val selected = pool.shuffled().take(10)
                selected.forEach { q ->
                    repository.insertDailySelection(q.id, today)
                }
                _state.value = DailyQuizState(questions = selected, isLoading = false)
            }
        }
    }

    fun selectAnswer(answer: String) {
        val current = _state.value
        if (current.isAnswerRevealed) return
        val currentQuestion = current.questions.getOrNull(current.currentIndex) ?: return
        val isCorrect = answer == currentQuestion.correctAnswer
        _state.value = current.copy(
            selectedAnswer = answer,
            isAnswerRevealed = true,
            score = if (isCorrect) current.score + 1 else current.score
        )
        repository.updateProgress(
            questionId = currentQuestion.id,
            attempts = 1,
            correctCount = if (isCorrect) 1L else 0L
        )
    }

    fun nextQuestion() {
        val current = _state.value
        val nextIndex = current.currentIndex + 1
        if (nextIndex >= current.questions.size) {
            _state.value = current.copy(isFinished = true)
        } else {
            _state.value = current.copy(
                currentIndex = nextIndex,
                selectedAnswer = null,
                isAnswerRevealed = false
            )
        }
    }

    fun restart() {
        val current = _state.value
        _state.value = DailyQuizState(questions = current.questions.shuffled(), isLoading = false)
    }
}