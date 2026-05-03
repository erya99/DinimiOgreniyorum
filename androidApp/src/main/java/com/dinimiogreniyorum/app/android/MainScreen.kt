package com.dinimiogreniyorum.app.android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.dinimiogreniyorum.app.StatsViewModel
import com.dinimiogreniyorum.app.DailyViewModel

data class CategoryItem(
    val title: String,
    val subtitle: String,
    val category: String,
    val emoji: String,
    val color: Color
)

val categories = listOf(
    CategoryItem("Doğru / Yanlış", "Bilgini test et", "TRUE_FALSE", "✅", Color(0xFF43A047)),
    CategoryItem("Kolay Sorular", "Temel bilgiler", "MULTI_CHOICE_EASY", "🟢", Color(0xFF66BB6A)),
    CategoryItem("Orta Sorular", "Biraz daha zor", "MULTI_CHOICE_MEDIUM", "🟡", Color(0xFFFFB300)),
    CategoryItem("Zor Sorular", "Ustalar için", "MULTI_CHOICE_HARD", "🔴", Color(0xFFE53935)),
    CategoryItem("Eşleştirme", "Doğru eşi bul", "MATCHING", "🔗", Color(0xFF8E24AA)),
    CategoryItem("Sure Soruları", "Hangi sure?", "SURAH_Q", "📖", Color(0xFF00897B)),
    CategoryItem("Sure Testi", "Sure adını bil", "SURAH_TEST", "🕌", Color(0xFF1E88E5))
)

@Composable
fun MainScreen(
    viewModel: QuizViewModel,
    statsViewModel: StatsViewModel,
    dailyViewModel: DailyViewModel
) {
    var selectedCategory by remember { mutableStateOf<CategoryItem?>(null) }
    var showStats by remember { mutableStateOf(false) }
    var showDaily by remember { mutableStateOf(false) }

    when {
        showDaily -> DailyScreen(
            viewModel = dailyViewModel,
            onBack = { showDaily = false }
        )
        showStats -> StatsScreen(
            statsViewModel = statsViewModel,
            onBack = { showStats = false }
        )
        selectedCategory != null -> QuizScreen(
            viewModel = viewModel,
            category = selectedCategory!!,
            onBack = { selectedCategory = null }
        )
        else -> HomeScreen(
            onCategorySelected = { selectedCategory = it },
            onStatsClicked = { showStats = true },
            onDailyClicked = { showDaily = true }
        )
    }
}

@Composable
fun HomeScreen(
    onCategorySelected: (CategoryItem) -> Unit,
    onStatsClicked: () -> Unit,
    onDailyClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header — istatistik butonu kaldırıldı
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(listOf(Color(0xFF2E7D32), Color(0xFF43A047)))
                )
                .padding(horizontal = 24.dp, vertical = 36.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("☪", fontSize = 40.sp, color = Color(0xFFFFCA28))
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Dinimİ Öğreniyorum",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Bir kategori seçerek başla",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Günlük soru banner
        Card(
            onClick = onDailyClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1565C0))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "🌙 Günün Soruları",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Her gün 10 yeni soru seni bekliyor!",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.2f)
                    )
                ) {
                    Text(
                        text = "Başla →",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { item ->
                CategoryCard(item = item, onClick = { onCategorySelected(item) })
            }
            // İstatistik kartı — grid'in son elemanı
            item {
                Card(
                    onClick = onStatsClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(6.dp)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                                .background(Color(0xFF5C6BC0))
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 16.dp, top = 12.dp, bottom = 12.dp, end = 8.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "📊", fontSize = 28.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "İstatistikler",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color(0xFF1B5E20)
                            )
                            Text(
                                text = "Gelişimini gör",
                                fontSize = 11.sp,
                                color = Color(0xFF757575)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCard(item: CategoryItem, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                    .background(item.color)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 12.dp, bottom = 12.dp, end = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = item.emoji, fontSize = 28.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Color(0xFF1B5E20)
                )
                Text(
                    text = item.subtitle,
                    fontSize = 11.sp,
                    color = Color(0xFF757575)
                )
            }
        }
    }
}