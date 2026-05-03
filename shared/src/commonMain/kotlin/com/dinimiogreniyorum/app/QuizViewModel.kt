package com.dinimiogreniyorum.app

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.dinimiogreniyorum.db.Question

data class QuizState(
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val score: Int = 0,
    val isFinished: Boolean = false,
    val selectedAnswer: String? = null,
    val isAnswerRevealed: Boolean = false
)

class QuizViewModel(
    private val repository: QuestionRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    private val _state = MutableStateFlow(QuizState())
    val state: StateFlow<QuizState> = _state

    fun loadQuestions(category: String, level: String) {
        scope.launch {
            val questions = repository.getQuestionsByLevel(category, level).shuffled()
            _state.value = QuizState(questions = questions)
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
        _state.value = QuizState(questions = current.questions.shuffled())
    }
}