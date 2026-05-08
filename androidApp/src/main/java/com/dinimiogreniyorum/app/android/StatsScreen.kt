package com.dinimiogreniyorum.app.android

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.dinimiogreniyorum.app.StatsViewModel

// Kategori isimleri ve emojileri (Değiştirilmedi)
val categoryNames = mapOf(
    "TRUE_FALSE" to "Doğru / Yanlış",
    "MULTI_CHOICE_EASY" to "Kolay Sorular",
    "MULTI_CHOICE_MEDIUM" to "Orta Sorular",
    "MULTI_CHOICE_HARD" to "Zor Sorular",
    "MATCHING" to "Eşleştirme",
    "SURAH_Q" to "Sure Soruları",
    "SURAH_TEST" to "Sure Testi",
    "DAILY_POOL" to "Günün Soruları",
    "GEN_TEMEL" to "Temel Bilgiler",
    "GEN_KURAN" to "Kur'an Bilgisi",
    "GEN_NAMAZ" to "Namaz",
    "GEN_ORUC" to "Oruç",
    "GEN_ZEKAT_HAC" to "Zekat ve Hac",
    "GEN_PEYGAMBERLER" to "Peygamberler",
    "GEN_SIYER" to "Hz. Muhammed",
    "GEN_HALIFELER" to "Dört Halife",
    "GEN_IMAN" to "İman Esasları",
    "GEN_MELEKLER" to "Melekler",
    "GEN_AHLAK_IBADET" to "Ahlak ve İbadet",
    "GEN_BAYRAMLAR" to "Bayramlar",
    "GEN_AHIRET" to "Cennet/Cehennem",
    "GEN_SURELER" to "Sureler",
    "GEN_TERIMLER" to "Dini Terimler",
    "GEN_TARIH" to "Tarihi Bilgiler",
    "GEN_DEGERLER" to "Ahlak/Değerler",
    "GEN_YAYILIS" to "İslam'ın Yayılışı",
    "GEN_EK_KURAN" to "Ek: İbadet",
    "GEN_EK_KAVRAMLAR" to "Ek: Kavramlar",
    "GEN_EK_TAMAM" to "Tamamlayıcı"
)

val categoryEmojis = mapOf(
    "TRUE_FALSE" to "✅",
    "MULTI_CHOICE_EASY" to "🟢",
    "MULTI_CHOICE_MEDIUM" to "🟡",
    "MULTI_CHOICE_HARD" to "🔴",
    "MATCHING" to "🔗",
    "SURAH_Q" to "📖",
    "SURAH_TEST" to "🕌",
    "DAILY_POOL" to "🌙",
    "GEN_TEMEL" to "🕋",
    "GEN_KURAN" to "📖",
    "GEN_NAMAZ" to "🧎",
    "GEN_ORUC" to "🌙",
    "GEN_ZEKAT_HAC" to "🕋",
    "GEN_PEYGAMBERLER" to "📜",
    "GEN_SIYER" to "🌹",
    "GEN_HALIFELER" to "⚖️",
    "GEN_IMAN" to "✨",
    "GEN_MELEKLER" to "👼",
    "GEN_AHLAK_IBADET" to "🤲",
    "GEN_BAYRAMLAR" to "🎉",
    "GEN_AHIRET" to "⚖️",
    "GEN_SURELER" to "📿",
    "GEN_TERIMLER" to "🔤",
    "GEN_TARIH" to "⏳",
    "GEN_DEGERLER" to "🤝",
    "GEN_YAYILIS" to "🌍",
    "GEN_EK_KURAN" to "📚",
    "GEN_EK_KAVRAMLAR" to "🧠",
    "GEN_EK_TAMAM" to "🧩"
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

    // Arka plan sarmalayıcısı (İstatistikler için bg_quiz kullanılır)
    IslamicBackground(screenType = ScreenType.STATISTICS) {
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
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Geri",
                            tint = Color(0xFF2D5A27)
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📊", fontSize = 28.sp)
                        Text(
                            text = "İstatistiklerim",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black, // Tombul başlık
                            color = Color(0xFF1B5E20)
                        )
                    }
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
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

                item {
                    val successRate = remember(state.totalAttempts, state.totalCorrect) {
                        if (state.totalAttempts > 0)
                            (state.totalCorrect * 100f / state.totalAttempts).toInt()
                        else 0
                    }

                    // Genel Başarı Kartı (Daha belirgin ve opak)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                        elevation = CardDefaults.cardElevation(2.dp),
                        border = BorderStroke(1.dp, Color(0xFFE8F5E9))
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Genel Başarı Oranı",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20).copy(alpha = 0.6f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "%$successRate",
                                fontSize = 42.sp,
                                fontWeight = FontWeight.Black,
                                color = when {
                                    successRate >= 80 -> Color(0xFF2E7D32)
                                    successRate >= 60 -> Color(0xFFFFB300)
                                    else -> Color(0xFFC62828)
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            LinearProgressIndicator(
                                progress = { successRate / 100f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(12.dp)
                                    .clip(CircleShape),
                                color = when {
                                    successRate >= 80 -> Color(0xFF43A047)
                                    successRate >= 60 -> Color(0xFFFFB300)
                                    else -> Color(0xFFF44336)
                                },
                                trackColor = Color.Black.copy(alpha = 0.05f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = when {
                                    successRate >= 80 -> "Mükemmel! 🏆"
                                    successRate >= 60 -> "İyi gidiyorsun! 👏"
                                    successRate >= 40 -> "Devam et! 💪"
                                    successRate > 0 -> "Daha çok çalış! 📖"
                                    else -> "Henüz soru çözmedin"
                                },
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1B5E20)
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = "Kategorilere Göre",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black, // Tombul
                        color = Color(0xFF1B5E20),
                        modifier = Modifier.padding(top = 8.dp, start = 4.dp)
                    )
                }

                if (state.categoryStats.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
                        ) {
                            Text(
                                text = "Henüz hiç soru çözmedin.\nHadi başla! 🚀",
                                modifier = Modifier.padding(24.dp).fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20).copy(alpha = 0.6f),
                                lineHeight = 22.sp
                            )
                        }
                    }
                } else {
                    items(state.categoryStats, key = { it.category }) { stat ->
                        val rate = remember(stat.attempted, stat.correct) {
                            if (stat.attempted > 0)
                                (stat.correct * 100f / stat.attempted).toInt()
                            else 0
                        }
                        CategoryStatCard(
                            emoji = categoryEmojis[stat.category] ?: "❓",
                            name = categoryNames[stat.category] ?: stat.category,
                            attempted = stat.attempted,
                            correct = stat.correct,
                            rate = rate
                        )
                    }
                }
                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
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
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(2.dp),
        border = BorderStroke(1.dp, Color(0xFFE8F5E9))
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = emoji, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF1B5E20)
            )
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20).copy(alpha = 0.5f),
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
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(1.dp),
        border = BorderStroke(1.dp, Color(0xFFE8F5E9))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = Color(0xFFF1F8E9),
                        shape = CircleShape,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = emoji, fontSize = 18.sp)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF1B5E20)
                    )
                }
                Text(
                    text = "%$rate",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = when {
                        rate >= 80 -> Color(0xFF2E7D32)
                        rate >= 60 -> Color(0xFFFFB300)
                        else -> Color(0xFFF44336)
                    }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = { rate / 100f },
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                color = when {
                    rate >= 80 -> Color(0xFF43A047)
                    rate >= 60 -> Color(0xFFFFB300)
                    else -> Color(0xFFF44336)
                },
                trackColor = Color.Black.copy(alpha = 0.05f)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "$attempted çözüldü · $correct doğru",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF9E9E9E)
            )
        }
    }
}