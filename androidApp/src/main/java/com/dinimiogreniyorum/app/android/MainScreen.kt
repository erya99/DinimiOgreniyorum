package com.dinimiogreniyorum.app.android

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dinimiogreniyorum.app.QuizViewModel
import com.dinimiogreniyorum.app.StatsViewModel
import com.dinimiogreniyorum.app.DailyViewModel

// Tasarımla uyumlu renkler
data class CategoryItem(
    val title: String,
    val subtitle: String,
    val category: String,
    val emoji: String,
    val color: Color
)

val categories = listOf(
    CategoryItem("Doğru / Yanlış", "Bilgini test et", "TRUE_FALSE", "✅", Color(0xFF4CAF50)),
    CategoryItem("Kolay Sorular", "Temel bilgiler", "MULTI_CHOICE_EASY", "🟢", Color(0xFF8BC34A)),
    CategoryItem("Orta Sorular", "Biraz daha zor", "MULTI_CHOICE_MEDIUM", "🟡", Color(0xFFFFC107)),
    CategoryItem("Zor Sorular", "Ustalar için", "MULTI_CHOICE_HARD", "🔴", Color(0xFFFF5722)),
    CategoryItem("Eşleştirme", "Doğru eşi bul", "MATCHING", "🔗", Color(0xFF9C27B0)),
    CategoryItem("Sure Soruları", "Hangi sure?", "SURAH_Q", "📖", Color(0xFF009688)),
    CategoryItem("Sure Testi", "Sure adını bil", "SURAH_TEST", "🕌", Color(0xFF03A9F4))
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

    BackHandler(enabled = showDaily || showStats || selectedCategory != null) {
        when {
            showDaily -> showDaily = false
            showStats -> showStats = false
            selectedCategory != null -> selectedCategory = null
        }
    }

    DinimOgreniyorumTheme { // Temayı burada uyguluyoruz
        Surface(color = MaterialTheme.colorScheme.background) {
            when {
                showDaily -> DailyScreen(viewModel = dailyViewModel, onBack = { showDaily = false })
                showStats -> StatsScreen(statsViewModel = statsViewModel, onBack = { showStats = false })
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
    }
}

@Composable
fun HomeScreen(
    onCategorySelected: (CategoryItem) -> Unit,
    onStatsClicked: () -> Unit,
    onDailyClicked: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Modern Header: Köşeleri yuvarlatılmış ve hafif kırpılmış
        Image(
            painter = painterResource(id = R.drawable.dinimiogreniyorum),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Günlük Soru Banner: Gradyan eklenerek canlandırıldı
        val dailyGradient = Brush.horizontalGradient(
            colors = listOf(Color(0xFF1976D2), Color(0xFF64B5F6))
        )

        Card(
            onClick = onDailyClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(dailyGradient)
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("🌙 Günün Soruları", style = MaterialTheme.typography.titleMedium, color = Color.White)
                    Text("10 yeni soru seni bekliyor!", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.9f))
                }
                Surface(
                    color = Color.White.copy(alpha = 0.25f),
                    shape = CircleShape
                ) {
                    Text("Başla →", modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {
            items(categories) { item ->
                CategoryCard(item = item, onClick = { onCategorySelected(item) })
            }
            item {
                // İstatistik Kartı: Özel tasarım
                CategoryCard(
                    item = CategoryItem("İstatistikler", "Gelişimini gör", "STATS", "📊", Color(0xFF5C6BC0)),
                    onClick = onStatsClicked
                )
            }
        }
    }
}

@Composable
fun CategoryCard(item: CategoryItem, onClick: () -> Unit) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            // height(120.dp) yerine heightIn kullanarak esneklik sağladık
            .heightIn(min = 110.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp), // Padding'i biraz daraltarak yazı alanını genişlettik
            verticalArrangement = Arrangement.Center
        ) {
            // İkon Konteynırı
            Surface(
                color = item.color.copy(alpha = 0.15f),
                shape = CircleShape,
                modifier = Modifier.size(36.dp) // Boyutu biraz küçülterek yer kazandık
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = item.emoji, fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.title,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 13.sp, // Fontu hafif küçülttük
                color = Color(0xFF2D5A27),
                lineHeight = 16.sp,
                maxLines = 2 // Başlık çok uzunsa 2 satıra kadar izin verir
            )

            Text(
                text = item.subtitle,
                fontSize = 10.sp,
                color = Color.Gray,
                lineHeight = 14.sp,
                maxLines = 2 // Açıklama artık kesilmeyecek
            )
        }
    }
}