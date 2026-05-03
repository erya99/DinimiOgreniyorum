package com.dinimiogreniyorum.app.android

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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

@Composable
fun MatchingScreen(
    viewModel: QuizViewModel,
    category: CategoryItem,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(category) {
        viewModel.loadQuestions(category.category, "EASY")
    }

    if (state.questions.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("📭", fontSize = 48.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Bu kategori için henüz soru yok", textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBack) { Text("Geri Dön") }
        }
        return
    }

    if (state.isFinished) {
        FinishScreen(
            score = state.score,
            total = state.questions.size,
            onRestart = { viewModel.restart() },
            onBack = onBack
        )
        return
    }

    val question = state.questions[state.currentIndex]
    val leftItems = question.options.split("|").filter { it.isNotBlank() }
    val rightItems = question.correctAnswer.split("|").filter { it.isNotBlank() }

    // Sağ listeyi karıştırılmış halde tut (sadece bir kez)
    val shuffledRight = remember(question.id) { rightItems.shuffled() }

    var selectedLeft by remember(question.id) { mutableStateOf<String?>(null) }
    var selectedRight by remember(question.id) { mutableStateOf<String?>(null) }
    val matched = remember(question.id) { mutableStateMapOf<String, String>() }
    var wrongPair by remember(question.id) { mutableStateOf<Pair<String, String>?>(null) }
    var isSetComplete by remember(question.id) { mutableStateOf(false) }

    // Eşleştirme kontrolü
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
                Text(text = category.emoji, fontSize = 24.sp)
                Text(
                    text = category.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

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
                    text = "Set ${state.currentIndex + 1} / ${state.questions.size}",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "✅ ${matched.size} / ${leftItems.size}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { (state.currentIndex + 1f) / state.questions.size },
                modifier = Modifier.fillMaxWidth().height(8.dp),
                color = Color(0xFF43A047),
                trackColor = Color(0xFFC8E6C9)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Soru başlığı
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Text(
                    text = question.questionText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = Color(0xFF1B5E20)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sol ve sağ listeler
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Sol liste
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    leftItems.forEach { item ->
                        val isMatched = matched.containsKey(item)
                        val isSelected = selectedLeft == item
                        val isWrong = wrongPair?.first == item

                        MatchingItem(
                            text = item,
                            isMatched = isMatched,
                            isSelected = isSelected,
                            isWrong = isWrong,
                            onClick = {
                                if (!isMatched) selectedLeft = item
                            }
                        )
                    }
                }

                // Sağ liste
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    shuffledRight.forEach { item ->
                        val isMatched = matched.containsValue(item)
                        val isSelected = selectedRight == item
                        val isWrong = wrongPair?.second == item

                        MatchingItem(
                            text = item,
                            isMatched = isMatched,
                            isSelected = isSelected,
                            isWrong = isWrong,
                            onClick = {
                                if (!isMatched) selectedRight = item
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tamamlandı mesajı ve devam butonu
            if (isSetComplete) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                ) {
                    Text(
                        text = "🎉 Tüm eşleştirmeler doğru!",
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { viewModel.nextQuestion() },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Text("Sonraki Set →", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
    val bgColor = when {
        isMatched -> Color(0xFFE8F5E9)
        isWrong -> Color(0xFFFFEBEE)
        isSelected -> Color(0xFFE3F2FD)
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
        isSelected -> Color(0xFF1565C0)
        else -> Color(0xFF1B5E20)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp)
            .background(bgColor, RoundedCornerShape(12.dp))
            .border(1.5.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable(enabled = !isMatched) { onClick() }
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isMatched) "✓ $text" else text,
            fontSize = 13.sp,
            fontWeight = if (isSelected || isMatched) FontWeight.Bold else FontWeight.Normal,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}