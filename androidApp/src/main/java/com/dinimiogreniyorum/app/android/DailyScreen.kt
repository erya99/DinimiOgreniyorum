package com.dinimiogreniyorum.app.android

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState // EKLENDİ
import androidx.compose.foundation.verticalScroll // EKLENDİ
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dinimiogreniyorum.app.DailyViewModel

@Composable
fun DailyScreen(
    viewModel: DailyViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState() // Kaydırma durumu eklendi

    LaunchedEffect(Unit) {
        viewModel.loadDailyQuestions()
    }

    // Arka plan sarmalayıcısı (bg_quiz görselini kullanır)
    IslamicBackground(screenType = ScreenType.QUIZ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // --- GLASSMORPHISM HEADER (Opaklık %70) ---
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
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Geri",
                            tint = Color(0xFF1565C0)
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("🌙", fontSize = 28.sp)
                        Text(
                            text = "Günün Soruları",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF0D47A1)
                        )
                    }
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }

            when {
                state.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF1E88E5))
                    }
                }

                state.questions.isEmpty() -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("📭", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Günlük sorular yüklenemedi",
                            textAlign = TextAlign.Center,
                            color = Color(0xFF0D47A1),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onBack,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
                        ) { Text("Geri Dön", fontWeight = FontWeight.Bold) }
                    }
                }

                state.isFinished -> {
                    DailyFinishScreen(
                        score = state.score,
                        total = state.questions.size,
                        onBack = onBack
                    )
                }

                else -> {
                    val question = state.questions[state.currentIndex]
                    val options = question.options.split("|").filter { it.isNotBlank() }

                    // Dış kolon - İçeriği ve butonu dikeyde ayırır
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                    ) {
                        // Soru ve Şıklar - Kaydırılabilir alan
                        Column(
                            modifier = Modifier
                                .weight(1f) // Butonu aşağı itmek için alanı kaplar
                                .verticalScroll(scrollState) // Uzun sorularda kaydırmayı sağlar
                        ) {
                            // --- SORU BİLGİ BARI (Opaklık %85 + Çerçeve) ---
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
                                        color = Color(0xFF0D47A1)
                                    )
                                    Text(
                                        text = "✅ ${state.score}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFF1565C0)
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
                                color = Color(0xFF1E88E5),
                                trackColor = Color.White.copy(alpha = 0.3f)
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Soru Kartı
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(28.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(2.dp),
                                border = BorderStroke(1.dp, Color(0xFFE3F2FD))
                            ) {
                                Text(
                                    text = question.questionText,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(28.dp),
                                    textAlign = TextAlign.Center,
                                    color = Color(0xFF0D47A1),
                                    lineHeight = 28.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Seçenekler
                            options.forEach { option ->
                                val isSelected = state.selectedAnswer == option
                                val isCorrect = option == question.correctAnswer
                                val bgColor = when {
                                    !state.isAnswerRevealed -> Color.White
                                    isCorrect -> Color(0xFFC8E6C9)
                                    isSelected -> Color(0xFFFFCDD2)
                                    else -> Color.White
                                }
                                val textColor = Color(0xFF0D47A1)

                                Card(
                                    onClick = { viewModel.selectAnswer(option) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 5.dp),
                                    shape = RoundedCornerShape(20.dp),
                                    colors = CardDefaults.cardColors(containerColor = bgColor),
                                    border = BorderStroke(
                                        width = if (isSelected) 2.5.dp else 1.dp,
                                        color = if (isSelected) Color(0xFF1565C0) else Color(0xFFE0E0E0)
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
                                            color = textColor,
                                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                            modifier = Modifier.weight(1f)
                                        )
                                        if (state.isAnswerRevealed) {
                                            Text(
                                                text = if (isCorrect) "✔" else if (isSelected) "✘" else "",
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Black,
                                                color = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336)
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                        }

                        // Sonraki Soru Butonu - Altta sabitlendi
                        if (state.isAnswerRevealed) {
                            Button(
                                onClick = { viewModel.nextQuestion() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp, top = 8.dp)
                                    .height(56.dp),
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
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
fun DailyFinishScreen(score: Int, total: Int, onBack: () -> Unit) {
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
        Text(text = emoji, fontSize = 80.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Günlük Tamamlandı!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF0D47A1)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$score / $total doğru",
            fontSize = 24.sp,
            color = Color(0xFF1E88E5),
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
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
        ) {
            Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = when {
                        percentage >= 80 -> "Mükemmel! Bugün çok iyisin 🌟"
                        percentage >= 60 -> "İyi gidiyorsun, yarın daha iyi! 💡"
                        percentage >= 40 -> "Biraz daha çalışman gerekiyor 📖"
                        else -> "Yarın tekrar dene! 🔄"
                    },
                    textAlign = TextAlign.Center,
                    color = Color(0xFF1565C0),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "🌙 Yeni sorular yarın geliyor!",
                    color = Color(0xFFF57F17),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // --- GÜNCELLENEN ANA MENÜ BUTONU (Opaklık Artırıldı: 0.9 + Glass) ---
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(18.dp)),
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(1.5.dp, Color(0xFF1565C0))
        ) {
            Text(
                "Ana Menü",
                fontSize = 17.sp,
                color = Color(0xFF1565C0),
                fontWeight = FontWeight.Black
            )
        }
    }
}