package com.dinimiogreniyorum.app.android

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// androidApp/src/main/java/com/dinimiogreniyorum/app/android/Theme.kt

// --- Renk Güncellemesi ---
val DeepGreen      = Color(0xFF2D5A27)
val LeafGreen      = Color(0xFF4CAF50)
val SoftMint       = Color(0xFFE8F5E9)
val WarmGold       = Color(0xFFFFC107)
// Arka planı daha kremsi ve yumuşak bir tona getirdik:
val WarmCream      = Color(0xFFFDF8E1)
val LightAmber     = Color(0xFFFFF2D0) // Yüzeyler için biraz daha belirgin sarı/krem
val TextOlive      = Color(0xFF1B3022)

private val LightColors = lightColorScheme(
    primary            = LeafGreen,
    onPrimary          = Color.White,
    primaryContainer   = SoftMint,
    onPrimaryContainer = DeepGreen,
    secondary          = WarmGold,
    onSecondary        = TextOlive,
    secondaryContainer = LightAmber,
    background         = WarmCream, // Ana arka plan artık kremsi
    surface            = Color.White, // Kartlar beyaz kalabilir, böylece krem üzerinde parlar
    onSurface          = TextOlive,
    surfaceVariant     = LightAmber,
    onSurfaceVariant   = DeepGreen
)

// --- Neşeli Bir UI İçin Yuvarlatılmış Köşeler ---
val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small      = RoundedCornerShape(12.dp),
    medium     = RoundedCornerShape(20.dp), // Kartlar için
    large      = RoundedCornerShape(28.dp), // Büyük bölümler için
    extraLarge = RoundedCornerShape(32.dp)
)

@Composable
fun DinimOgreniyorumTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        shapes      = AppShapes, // Şekilleri buraya bağladık
        typography  = Typography(), // Mevcut tipografini kullanır
        content     = content
    )
}