package com.dinimiogreniyorum.app.android

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
    CategoryItem("Sure Testi", "Sure adını bil", "SURAH_TEST", "🕌", Color(0xFF03A9F4)),
    CategoryItem("Genel Testler", "Tüm Konular", "GENERAL_MENU", "📚", Color(0xFFE91E63)) // Yeni Genel Test Butonu
)

// 21 Başlıklı Genel Test Alt Kategorileri
val generalSubCategories = listOf(
    CategoryItem("Temel Bilgiler", "İslam'ın şartları", "GEN_TEMEL", "🕋", Color(0xFF4CAF50)),
    CategoryItem("Kur'an Bilgisi", "Kur'an-ı Kerim", "GEN_KURAN", "📖", Color(0xFF009688)),
    CategoryItem("Namaz", "Namaz ibadeti", "GEN_NAMAZ", "🧎", Color(0xFF03A9F4)),
    CategoryItem("Oruç", "Ramazan ve Oruç", "GEN_ORUC", "🌙", Color(0xFF3F51B5)),
    CategoryItem("Zekat ve Hac", "Mali ve bedeni", "GEN_ZEKAT_HAC", "🕋", Color(0xFF9C27B0)),
    CategoryItem("Peygamberler", "Peygamberler tarihi", "GEN_PEYGAMBERLER", "📜", Color(0xFFE91E63)),
    CategoryItem("Hz. Muhammed", "Siyer-i Nebi", "GEN_SIYER", "🌹", Color(0xFFF44336)),
    CategoryItem("Dört Halife", "Hulefa-i Raşidin", "GEN_HALIFELER", "⚖️", Color(0xFFFF5722)),
    CategoryItem("İman Esasları", "Amentü", "GEN_IMAN", "✨", Color(0xFFFF9800)),
    CategoryItem("Melekler", "Meleklere iman", "GEN_MELEKLER", "👼", Color(0xFFFFC107)),
    CategoryItem("Ahlak ve İbadet", "İbadet şuuru", "GEN_AHLAK_IBADET", "🤲", Color(0xFF8BC34A)),
    CategoryItem("Bayramlar", "Özel Günler", "GEN_BAYRAMLAR", "🎉", Color(0xFFCDDC39)),
    CategoryItem("Cennet/Cehennem", "Ahiret inancı", "GEN_AHIRET", "⚖️", Color(0xFF607D8B)),
    CategoryItem("Sureler", "Kur'an'dan Sureler", "GEN_SURELER", "📿", Color(0xFF795548)),
    CategoryItem("Dini Terimler", "Arapça kavramlar", "GEN_TERIMLER", "🔤", Color(0xFF9E9E9E)),
    CategoryItem("Tarihi Bilgiler", "İslam Tarihi", "GEN_TARIH", "⏳", Color(0xFF5C6BC0)),
    CategoryItem("Ahlak/Değerler", "İslami Değerler", "GEN_DEGERLER", "🤝", Color(0xFF26A69A)),
    CategoryItem("İslam'ın Yayılışı", "İlk yıllar", "GEN_YAYILIS", "🌍", Color(0xFF00BCD4)),
    CategoryItem("Ek: İbadet", "Detaylı sorular", "GEN_EK_KURAN", "📚", Color(0xFF3F51B5)),
    CategoryItem("Ek: Kavramlar", "Kavram testi", "GEN_EK_KAVRAMLAR", "🧠", Color(0xFF673AB7)),
    CategoryItem("Tamamlayıcı", "Karışık test", "GEN_EK_TAMAM", "🧩", Color(0xFFE91E63))
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
    var showGeneralMenu by remember { mutableStateOf(false) }

    BackHandler(enabled = showDaily || showStats || showGeneralMenu || selectedCategory != null) {
        when {
            showDaily -> showDaily = false
            showStats -> showStats = false
            showGeneralMenu -> showGeneralMenu = false
            selectedCategory != null -> selectedCategory = null
        }
    }

    DinimOgreniyorumTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            when {
                showDaily -> DailyScreen(viewModel = dailyViewModel, onBack = { showDaily = false })
                showStats -> StatsScreen(statsViewModel = statsViewModel, onBack = { showStats = false })
                showGeneralMenu -> GeneralMenuScreen(
                    onSubCategorySelected = {
                        selectedCategory = it
                        showGeneralMenu = false
                    },
                    onBack = { showGeneralMenu = false }
                )
                selectedCategory != null -> QuizScreen(
                    viewModel = viewModel,
                    category = selectedCategory!!,
                    onBack = { selectedCategory = null }
                )
                else -> HomeScreen(
                    onCategorySelected = {
                        if (it.category == "GENERAL_MENU") {
                            showGeneralMenu = true
                        } else {
                            selectedCategory = it
                        }
                    },
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
                CategoryCard(
                    item = CategoryItem("İstatistikler", "Gelişimini gör", "STATS", "📊", Color(0xFF5C6BC0)),
                    onClick = onStatsClicked
                )
            }
        }
    }
}

// Yeni Alt Menü Ekranı (2 Sütunlu Izgara)
@Composable
fun GeneralMenuScreen(
    onSubCategorySelected: (CategoryItem) -> Unit,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { onBack() }
                    .padding(end = 16.dp)
            )
            Text(
                text = "Genel Testler",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D5A27)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 21 Kategori olduğu için 2 sütunlu yapıldı
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {
            items(generalSubCategories) { item ->
                CategoryCard(item = item, onClick = { onSubCategorySelected(item) })
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
            .heightIn(min = 110.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                color = item.color.copy(alpha = 0.15f),
                shape = CircleShape,
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = item.emoji, fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.title,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 13.sp,
                color = Color(0xFF2D5A27),
                lineHeight = 16.sp,
                maxLines = 2
            )

            Text(
                text = item.subtitle,
                fontSize = 10.sp,
                color = Color.Gray,
                lineHeight = 14.sp,
                maxLines = 2
            )
        }
    }
}