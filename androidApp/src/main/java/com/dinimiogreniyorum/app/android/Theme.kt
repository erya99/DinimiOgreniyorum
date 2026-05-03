package com.dinimiogreniyorum.app.android

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// İslami yeşil tema renkleri
val Green900 = Color(0xFF1B5E20)
val Green700 = Color(0xFF388E3C)
val Green500 = Color(0xFF4CAF50)
val Green100 = Color(0xFFC8E6C9)
val Gold400  = Color(0xFFFFCA28)
val Gold200  = Color(0xFFFFF176)
val Cream    = Color(0xFFFFFDE7)
val DarkBg   = Color(0xFF121212)

private val LightColors = lightColorScheme(
    primary          = Green700,
    onPrimary        = Color.White,
    primaryContainer = Green100,
    onPrimaryContainer = Green900,
    secondary        = Gold400,
    onSecondary      = Green900,
    secondaryContainer = Gold200,
    background       = Cream,
    onBackground     = Green900,
    surface          = Color.White,
    onSurface        = Green900,
    surfaceVariant   = Color(0xFFE8F5E9),
    onSurfaceVariant = Color(0xFF2E7D32)
)

@Composable
fun DinimOgreniyorumTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(),
        content = content
    )
}