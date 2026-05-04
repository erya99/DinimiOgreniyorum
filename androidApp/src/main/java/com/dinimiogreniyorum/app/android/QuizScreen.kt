package com.dinimiogreniyorum.app.android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.dinimiogreniyorum.app.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    category: CategoryItem,
    onBack: () -> Unit
) {

    // MATCHING kategorisi MatchingScreen'e yönlendir
    if (category.category == "MATCHING") {
        MatchingScreen(viewModel = viewModel, category = category, onBack = onBack)
        return
    }

    val state by viewModel.state.collectAsState()

    LaunchedEffect(category) {
        val level = when (category.category) {
            "MULTI_CHOICE_MEDIUM" -> "MEDIUM"
            "MULTI_CHOICE_HARD" -> "HARD"
            else -> "EASY"
        }
        viewModel.loadQuestions(category.category, level)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // YENİLENEN HEADER: Row kullanılarak ikon ve başlık dikeyde tam hizalandı
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(listOf(Color(0xFF2E7D32), Color(0xFF43A047)))
                )
                .padding(horizontal = 12.dp, vertical = 20.dp) // padding biraz toparlandı
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically // Ok ve Yazıyı dikeyde merkeze alır
            ) {
                // Sol taraftaki geri oku
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Geri",
                        tint = Color.White
                    )
                }

                // Geri kalan alanı kapla ve içindeki yazıları merkeze hizala
                Column(
                    modifier = Modifier.weight(1f).padding(end = 48.dp), // Okun kapladığı alan kadar sağdan boşluk (tam ortalamak için)
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = category.emoji,
                        fontSize = 24.sp
                    )
                    Text(
                        text = category.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        when {
            state.questions.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("📭", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Bu kategori için henüz soru yok",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onBack) { Text("Geri Dön") }
                }
            }

            state.isFinished -> {
                FinishScreen(
                    score = state.score,
                    total = state.questions.size,
                    onRestart = { viewModel.restart() },
                    onBack = onBack
                )
            }

            else -> {
                val question = state.questions[state.currentIndex]
                val options = question.options.split("|").filter { it.isNotBlank() }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // İlerleme
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Soru ${state.currentIndex + 1} / ${state.questions.size}",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "✅ ${state.score}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { (state.currentIndex + 1f) / state.questions.size },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = Color(0xFF43A047),
                        trackColor = Color(0xFFC8E6C9),
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    // Soru kartı
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Text(
                            text = question.questionText,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(24.dp),
                            textAlign = TextAlign.Center,
                            color = Color(0xFF1B5E20),
                            lineHeight = 26.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Cevap seçenekleri
                    options.forEach { option ->
                        val isSelected = state.selectedAnswer == option
                        val isCorrect = option == question.correctAnswer
                        val bgColor = when {
                            !state.isAnswerRevealed -> Color.White
                            isCorrect -> Color(0xFFE8F5E9)
                            isSelected -> Color(0xFFFFEBEE)
                            else -> Color.White
                        }
                        val borderColor = when {
                            !state.isAnswerRevealed -> Color(0xFFE0E0E0)
                            isCorrect -> Color(0xFF4CAF50)
                            isSelected -> Color(0xFFF44336)
                            else -> Color(0xFFE0E0E0)
                        }
                        val textColor = when {
                            !state.isAnswerRevealed -> Color(0xFF1B5E20)
                            isCorrect -> Color(0xFF2E7D32)
                            isSelected -> Color(0xFFC62828)
                            else -> Color(0xFF757575)
                        }

                        Card(
                            onClick = { viewModel.selectAnswer(option) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = bgColor),
                            border = CardDefaults.outlinedCardBorder().copy(
                                width = 1.5.dp
                            ),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = option,
                                    fontSize = 15.sp,
                                    color = textColor,
                                    fontWeight = if (isSelected || (state.isAnswerRevealed && isCorrect))
                                        FontWeight.Bold else FontWeight.Normal
                                )
                                if (state.isAnswerRevealed) {
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        text = if (isCorrect) "✓" else if (isSelected) "✗" else "",
                                        fontSize = 18.sp,
                                        color = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336)
                                    )
                                }
                            }
                        }
                    }

                    if (state.isAnswerRevealed) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.nextQuestion() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2E7D32)
                            )
                        ) {
                            Text(
                                "Sonraki Soru →",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FinishScreen(score: Int, total: Int, onRestart: () -> Unit, onBack: () -> Unit) {
    val percentage = (score * 100f / total).toInt()
    val emoji = when {
        percentage >= 80 -> "🏆"
        percentage >= 60 -> "👏"
        percentage >= 40 -> "💪"
        else -> "📚"
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = emoji, fontSize = 72.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Tebrikler!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$score / $total doğru",
            fontSize = 22.sp,
            color = Color(0xFF43A047),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "%$percentage başarı",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
        ) {
            Text(
                text = when {
                    percentage >= 80 -> "Mükemmel! Bilgin çok iyi 🌟"
                    percentage >= 60 -> "İyi gidiyorsun, devam et! 💡"
                    percentage >= 40 -> "Biraz daha çalışman gerekiyor 📖"
                    else -> "Tekrar çalışmanı öneririm 🔄"
                },
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                color = Color(0xFF2E7D32),
                fontSize = 15.sp
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onRestart,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
        ) {
            Text("Tekrar Oyna", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Ana Menü", fontSize = 16.sp, color = Color(0xFF2E7D32))
        }
    }
}