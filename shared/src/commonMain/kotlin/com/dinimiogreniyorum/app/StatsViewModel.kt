package com.dinimiogreniyorum.app

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CategoryStat(
    val category: String,
    val attempted: Long,
    val correct: Long
)

data class StatsState(
    val totalQuestions: Long = 0,
    val totalAttempts: Long = 0,
    val totalCorrect: Long = 0,
    val categoryStats: List<CategoryStat> = emptyList()
)

class StatsViewModel(
    private val repository: QuestionRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    private val _state = MutableStateFlow(StatsState())
    val state: StateFlow<StatsState> = _state

    fun loadStats() {
        scope.launch {
            val categoryStats = repository.getProgressByCategory().map {
                CategoryStat(
                    category = it.category,
                    attempted = it.attemptedCount,
                    correct = it.correctTotal?.toLong() ?: 0L
                )
            }
            _state.value = StatsState(
                totalQuestions = repository.getTotalQuestions(),
                totalAttempts = repository.getTotalAttempts(),
                totalCorrect = repository.getTotalCorrect(),
                categoryStats = categoryStats
            )
        }
    }
}