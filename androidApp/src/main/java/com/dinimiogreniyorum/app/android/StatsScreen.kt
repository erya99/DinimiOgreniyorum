package com.dinimiogreniyorum.app.android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dinimiogreniyorum.app.StatsViewModel

val categoryNames = mapOf(
    "TRUE_FALSE" to "Doğru / Yanlış",
    "MULTI_CHOICE_EASY" to "Kolay Sorular",
    "MULTI_CHOICE_MEDIUM" to "Orta Sorular",
    "MULTI_CHOICE_HARD" to "Zor Sorular",
    "MATCHING" to "Eşleştirme",
    "SURAH_Q" to "Sure Soruları",
    "SURAH_TEST" to "Sure Testi"
)

val categoryEmojis = mapOf(
    "TRUE_FALSE" to "✅",
    "MULTI_CHOICE_EASY" to "🟢",
    "MULTI_CHOICE_MEDIUM" to "🟡",
    "MULTI_CHOICE_HARD" to "🔴",
    "MATCHING" to "🔗",
    "SURAH_Q" to "📖",
    "SURAH_TEST" to "🕌"
)

@Composable
fun StatsScreen(
    statsViewModel: StatsViewModel,
    onBack: () -> Unit
) {
    val state by statsViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        statsViewModel.loadStats()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(listOf(Color(0xFF2E7D32), Color(0xFF43A047)))
                )
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Geri",
                    tint = Color.White
                )
            }
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("📊", fontSize = 24.sp)
                Text(
                    text = "İstatistiklerim",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Genel özet kartları
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        emoji = "📚",
                        value = state.totalQuestions.toString(),
                        label = "Toplam Soru"
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        emoji = "🎯",
                        value = state.totalAttempts.toString(),
                        label = "Çözülen"
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        emoji = "✅",
                        value = state.totalCorrect.toString(),
                        label = "Doğru"
                    )
                }
            }

            // Genel başarı oranı
            item {
                val successRate = if (state.totalAttempts > 0)
                    (state.totalCorrect * 100f / state.totalAttempts).toInt()
                else 0

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Genel Başarı Oranı",
                            fontSize = 14.sp,
                            color = Color(0xFF757575)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "%$successRate",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = when {
                                successRate >= 80 -> Color(0xFF2E7D32)
                                successRate >= 60 -> Color(0xFFFFB300)
                                else -> Color(0xFFC62828)
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { successRate / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp),
                            color = when {
                                successRate >= 80 -> Color(0xFF43A047)
                                successRate >= 60 -> Color(0xFFFFB300)
                                else -> Color(0xFFF44336)
                            },
                            trackColor = Color(0xFFE0E0E0)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = when {
                                successRate >= 80 -> "Mükemmel! 🏆"
                                successRate >= 60 -> "İyi gidiyorsun! 👏"
                                successRate >= 40 -> "Devam et! 💪"
                                successRate > 0 -> "Daha çok çalış! 📖"
                                else -> "Henüz soru çözmedin"
                            },
                            fontSize = 13.sp,
                            color = Color(0xFF757575)
                        )
                    }
                }
            }

            // Kategori başlığı
            item {
                Text(
                    text = "Kategorilere Göre",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Kategori istatistikleri
            if (state.categoryStats.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = "Henüz hiç soru çözmedin.\nHadi başla! 🚀",
                            modifier = Modifier
                                .padding(24.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color(0xFF757575),
                            lineHeight = 22.sp
                        )
                    }
                }
            } else {
                items(state.categoryStats) { stat ->
                    val rate = if (stat.attempted > 0)
                        (stat.correct * 100f / stat.attempted).toInt()
                    else 0
                    CategoryStatCard(
                        emoji = categoryEmojis[stat.category] ?: "❓",
                        name = categoryNames[stat.category] ?: stat.category,
                        attempted = stat.attempted,
                        correct = stat.correct,
                        rate = rate
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    emoji: String,
    value: String,
    label: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = emoji, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20)
            )
            Text(
                text = label,
                fontSize = 11.sp,
                color = Color(0xFF757575),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CategoryStatCard(
    emoji: String,
    name: String,
    attempted: Long,
    correct: Long,
    rate: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = emoji, fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B5E20)
                    )
                }
                Text(
                    text = "%$rate",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        rate >= 80 -> Color(0xFF2E7D32)
                        rate >= 60 -> Color(0xFFFFB300)
                        else -> Color(0xFFF44336)
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { rate / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = when {
                    rate >= 80 -> Color(0xFF43A047)
                    rate >= 60 -> Color(0xFFFFB300)
                    else -> Color(0xFFF44336)
                },
                trackColor = Color(0xFFE0E0E0)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$attempted çözüldü · $correct doğru",
                fontSize = 12.sp,
                color = Color(0xFF9E9E9E)
            )
        }
    }
}