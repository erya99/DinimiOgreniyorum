package com.dinimiogreniyorum.app.android

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.dinimiogreniyorum.app.QuizViewModel

@Composable
fun MatchingScreen(
    viewModel: QuizViewModel,
    category: CategoryItem,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState() // Kaydırma durumu eklendi

    LaunchedEffect(category) {
        viewModel.loadQuestions(category.category, "EASY")
    }

    // Arka plan sarmalayıcısı
    IslamicBackground(screenType = ScreenType.QUIZ) {
        if (state.questions.isEmpty()) {
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
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onBack,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) { Text("Geri Dön", fontWeight = FontWeight.Bold) }
            }
            return@IslamicBackground
        }

        if (state.isFinished) {
            FinishScreen(
                score = state.score,
                total = state.questions.size,
                onRestart = { viewModel.restart() },
                onBack = onBack
            )
            return@IslamicBackground
        }

        val question = state.questions[state.currentIndex]
        val leftItems = question.options.split("|").filter { it.isNotBlank() }
        val rightItems = question.correctAnswer.split("|").filter { it.isNotBlank() }
        val shuffledRight = remember(question.id) { rightItems.shuffled() }

        var selectedLeft by remember(question.id) { mutableStateOf<String?>(null) }
        var selectedRight by remember(question.id) { mutableStateOf<String?>(null) }
        val matched = remember(question.id) { mutableStateMapOf<String, String>() }
        var wrongPair by remember(question.id) { mutableStateOf<Pair<String, String>?>(null) }
        var isSetComplete by remember(question.id) { mutableStateOf(false) }

        LaunchedEffect(selectedLeft, selectedRight) {
            val l = selectedLeft
            val r = selectedRight
            if (l != null && r != null) {
                val leftIndex = leftItems.indexOf(l)
                val correctRight = rightItems.getOrNull(leftIndex)
                if (r == correctRight) {
                    matched[l] = r
                    selectedLeft = null
                    selectedRight = null
                    wrongPair = null
                    if (matched.size == leftItems.size) {
                        isSetComplete = true
                    }
                } else {
                    wrongPair = Pair(l, r)
                    kotlinx.coroutines.delay(700)
                    selectedLeft = null
                    selectedRight = null
                    wrongPair = null
                }
            }
        }

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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Geri", tint = Color(0xFF2D5A27))
                    }
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
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

            // İçerik Kolonu (Sabit butonu desteklemek için ikiye ayrıldı)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                // Kaydırılabilir Alan
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState)
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
                                text = "Set ${state.currentIndex + 1} / ${state.questions.size}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF1B5E20)
                            )
                            Text(
                                text = "✅ ${matched.size} / ${leftItems.size}",
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
                        trackColor = Color.White.copy(alpha = 0.3f)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Soru Başlık Kartı (Mat Beyaz)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp),
                        border = BorderStroke(1.dp, Color(0xFFE8F5E9))
                    ) {
                        Text(
                            text = question.questionText,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(20.dp),
                            textAlign = TextAlign.Center,
                            color = Color(0xFF1B5E20)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Eşleştirme Listeleri
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Sol liste
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            leftItems.forEach { item ->
                                val isMatched = matched.containsKey(item)
                                val isSelected = selectedLeft == item
                                val isWrong = wrongPair?.first == item
                                MatchingItem(text = item, isMatched = isMatched, isSelected = isSelected, isWrong = isWrong, onClick = { if (!isMatched) selectedLeft = item })
                            }
                        }

                        // Sağ liste
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            shuffledRight.forEach { item ->
                                val isMatched = matched.containsValue(item)
                                val isSelected = selectedRight == item
                                val isWrong = wrongPair?.second == item
                                MatchingItem(text = item, isMatched = isMatched, isSelected = isSelected, isWrong = isWrong, onClick = { if (!isMatched) selectedRight = item })
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Set Tamamlandı Kartı (Mat)
                    if (isSetComplete) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                            border = BorderStroke(1.dp, Color(0xFF4CAF50))
                        ) {
                            Text(
                                text = "🎉 Tüm eşleştirmeler doğru!",
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = Color(0xFF2E7D32),
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(80.dp)) // Buton için boşluk
                    }
                }

                // Devam Butonu - En alta sabitlendi
                if (isSetComplete) {
                    Button(
                        onClick = { viewModel.nextQuestion() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp, top = 8.dp)
                            .height(56.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                    ) {
                        Text("Sonraki Set →", fontSize = 17.sp, fontWeight = FontWeight.Black)
                    }
                }
            }
        }
    }
}

@Composable
fun MatchingItem(
    text: String,
    isMatched: Boolean,
    isSelected: Boolean,
    isWrong: Boolean,
    onClick: () -> Unit
) {
    // Şıklar artık şeffaf değil, mat (opak) renkler kullanıldı
    val bgColor = when {
        isMatched -> Color(0xFFC8E6C9)
        isWrong -> Color(0xFFFFCDD2)
        isSelected -> Color(0xFFBBDEFB)
        else -> Color.White
    }
    val borderColor = when {
        isMatched -> Color(0xFF4CAF50)
        isWrong -> Color(0xFFF44336)
        isSelected -> Color(0xFF1E88E5)
        else -> Color(0xFFE0E0E0)
    }
    val textColor = when {
        isMatched -> Color(0xFF2E7D32)
        isWrong -> Color(0xFFC62828)
        isSelected -> Color(0xFF0D47A1)
        else -> Color(0xFF1B5E20)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 60.dp)
            .background(bgColor, RoundedCornerShape(14.dp))
            .border(BorderStroke(if (isSelected) 2.dp else 1.2.dp, borderColor), RoundedCornerShape(14.dp))
            .clickable(enabled = !isMatched) { onClick() }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isMatched) "✓ $text" else text,
            fontSize = 14.sp,
            fontWeight = if (isSelected || isMatched) FontWeight.Black else FontWeight.Bold,
            color = textColor,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
    }
}