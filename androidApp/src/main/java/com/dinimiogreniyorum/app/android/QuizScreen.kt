package com.dinimiogreniyorum.app.android

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState // EKLENDİ: Kaydırma için
import androidx.compose.foundation.verticalScroll // EKLENDİ: Kaydırma için
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    category: CategoryItem,
    onBack: () -> Unit
) {
    if (category.category == "MATCHING") {
        MatchingScreen(viewModel = viewModel, category = category, onBack = onBack)
        return
    }

    val state by viewModel.state.collectAsState()
    // Kaydırma durumu (Scroll state) eklendi
    val scrollState = rememberScrollState()

    LaunchedEffect(category) {
        val level = when (category.category) {
            "MULTI_CHOICE_MEDIUM" -> "MEDIUM"
            "MULTI_CHOICE_HARD" -> "HARD"
            else -> "EASY"
        }
        viewModel.loadQuestions(category.category, level)
    }

    IslamicBackground(screenType = ScreenType.QUIZ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // --- HEADER (Opaklık %70) ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White.copy(alpha = 0.7f))
                    .border(BorderStroke(1.dp, Color.White), RoundedCornerShape(24.dp))
                    .padding(vertical = 12.dp, horizontal = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.background(Color.White.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Geri",
                            tint = Color(0xFF2D5A27)
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = category.emoji, fontSize = 28.sp)
                        Text(
                            text = category.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF1B5E20)
                        )
                    }
                    Spacer(modifier = Modifier.width(48.dp))
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
                            color = Color(0xFF1B5E20),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onBack,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                        ) {
                            Text("Geri Dön", fontWeight = FontWeight.Bold)
                        }
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

                    // Dış kolon - İçeriği ve butonu ayırır
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                    ) {
                        // Soru ve Şıklar - Bu kısım kaydırılabilir (Scrollable) yapıldı
                        Column(
                            modifier = Modifier
                                .weight(1f) // Kalan alanı doldurur
                                .verticalScroll(scrollState) // Uzun sorularda butonun aşağı kaçmasını engeller
                        ) {
                            // --- GÜNCELLENEN SORU BİLGİ BARI (Opaklık %85 + Çerçeve) ---
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White.copy(alpha = 0.85f))
                                    .border(BorderStroke(1.dp, Color.White), RoundedCornerShape(16.dp))
                                    .padding(horizontal = 16.dp, vertical = 10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Soru ${state.currentIndex + 1} / ${state.questions.size}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFF1B5E20)
                                    )
                                    Text(
                                        text = "Doğru: ${state.score}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFF2E7D32)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            LinearProgressIndicator(
                                progress = { (state.currentIndex + 1f) / state.questions.size },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                                    .clip(CircleShape),
                                color = Color(0xFF43A047),
                                trackColor = Color.White.copy(alpha = 0.3f),
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Soru Kartı (Opak Beyaz)
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(28.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(2.dp),
                                border = BorderStroke(1.dp, Color(0xFFE8F5E9))
                            ) {
                                Text(
                                    text = question.questionText,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(28.dp),
                                    textAlign = TextAlign.Center,
                                    color = Color(0xFF1B3022),
                                    lineHeight = 28.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Cevap Seçenekleri
                            options.forEach { option ->
                                val isSelected = state.selectedAnswer == option
                                val isCorrect = option == question.correctAnswer

                                val containerColor = when {
                                    !state.isAnswerRevealed -> Color.White
                                    isCorrect -> Color(0xFFC8E6C9)
                                    isSelected -> Color(0xFFFFCDD2)
                                    else -> Color.White
                                }

                                Card(
                                    onClick = { viewModel.selectAnswer(option) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 5.dp),
                                    shape = RoundedCornerShape(20.dp),
                                    colors = CardDefaults.cardColors(containerColor = containerColor),
                                    border = BorderStroke(
                                        width = if (isSelected) 2.5.dp else 1.dp,
                                        color = if (isSelected) Color(0xFF2E7D32) else Color(0xFFE0E0E0)
                                    ),
                                    elevation = CardDefaults.cardElevation(if (isSelected) 4.dp else 1.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(18.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = option,
                                            fontSize = 16.sp,
                                            color = Color(0xFF1B3022),
                                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                            modifier = Modifier.weight(1f)
                                        )
                                        if (state.isAnswerRevealed) {
                                            Text(
                                                text = if (isCorrect) "✔" else if (isSelected) "✘" else "",
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Black,
                                                color = if (isCorrect) Color(0xFF2E7D32) else Color(0xFFD32F2F)
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp)) // Kaydırma sonu boşluğu
                        }

                        // Sonraki Soru Butonu - En alta sabitlendi
                        if (state.isAnswerRevealed) {
                            Button(
                                onClick = { viewModel.nextQuestion() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp, top = 8.dp)
                                    .height(56.dp),
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF2E7D32)
                                )
                            ) {
                                Text("Sonraki Soru →", fontSize = 17.sp, fontWeight = FontWeight.Black)
                            }
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
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = emoji, fontSize = 80.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Tebrikler!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF1B5E20)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$score / $total doğru",
            fontSize = 24.sp,
            color = Color(0xFF43A047),
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = "%$percentage başarı",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Text(
                text = when {
                    percentage >= 80 -> "Mükemmel! Bilgin çok iyi 🌟"
                    percentage >= 60 -> "İyi gidiyorsun, devam et! 💡"
                    percentage >= 40 -> "Biraz daha çalışman gerekiyor 📖"
                    else -> "Tekrar çalışmanı öneririm 🔄"
                },
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center,
                color = Color(0xFF2E7D32),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = onRestart,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
        ) {
            Text("Tekrar Dene", fontSize = 17.sp, fontWeight = FontWeight.Black)
        }
        Spacer(modifier = Modifier.height(12.dp))

        // --- GÜNCELLENEN ANA MENÜ BUTONU (Opaklık Artırıldı: 0.9 + Glass) ---
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(18.dp)),
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.5.dp, Color(0xFF2E7D32))
        ) {
            Text(
                "Ana Menü",
                fontSize = 17.sp,
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.Black
            )
        }
    }
}