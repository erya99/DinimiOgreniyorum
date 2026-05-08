package com.dinimiogreniyorum.app.android

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource

enum class ScreenType { HOME, QUIZ, STATISTICS }

@Composable
fun IslamicBackground(
    screenType: ScreenType,
    content: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Görsel seçimi: Dikey ve Yatay WebP dosyalarını eşleştiriyoruz
    val backgroundImage = when (screenType) {
        ScreenType.HOME -> {
            if (isLandscape) painterResource(id = R.drawable.bg_homeland)
            else painterResource(id = R.drawable.bg_home)
        }
        else -> { // Quiz, İstatistik, Günlük Soru vb. için bg_quiz kullanılır
            if (isLandscape) painterResource(id = R.drawable.bg_quizland)
            else painterResource(id = R.drawable.bg_quiz)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop, // Görüntü oranını bozmadan ekranı doldurur
            alpha = 0.8f // İçeriğin okunabilirliği için hafif şeffaflık
        )

        content()
    }
}