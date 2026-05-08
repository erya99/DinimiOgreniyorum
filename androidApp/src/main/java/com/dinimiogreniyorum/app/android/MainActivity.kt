package com.dinimiogreniyorum.app.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding // EKLENDİ: Alt ve üst sistem çubukları için
import androidx.compose.material3.MaterialTheme // GÜNCELLEME İÇİN EKLENDİ
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dinimiogreniyorum.app.DailyViewModel
import com.dinimiogreniyorum.app.QuestionRepository
import com.dinimiogreniyorum.app.QuizViewModel
import com.dinimiogreniyorum.app.SeedData
import com.dinimiogreniyorum.app.StatsViewModel
import com.dinimiogreniyorum.db.DatabaseDriverFactory
import com.dinimiogreniyorum.db.createDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Splash Screen'i başlat
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // Manuel tablo oluşturma (dbHelper) kodları BURADAN SİLİNDİ.
        // SQLDelight tabloları AppDatabase.sq üzerinden otomatik yönetecek.

        val database = createDatabase(DatabaseDriverFactory(this))
        val repository = QuestionRepository(database)
        val viewModel = QuizViewModel(repository)
        val statsViewModel = StatsViewModel(repository)
        val dailyViewModel = DailyViewModel(repository)
        SeedData(repository).seedIfEmpty()

        setContent {
            DinimOgreniyorumTheme {
                // EKLENDİ: systemBarsPadding() eklendi. Artık ekranın altındaki telefon menüsü uygulamanı yutmayacak.
                // GÜNCELLEME: Surface rengi 'background' olarak ayarlandı.
                // Böylece Theme.kt'deki kremsi arka plan devreye girecek ve IslamicBackground motifleri görünür olacak.
                Surface(
                    modifier = Modifier.fillMaxSize().systemBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        viewModel = viewModel,
                        statsViewModel = statsViewModel,
                        dailyViewModel = dailyViewModel
                    )
                }
            }
        }
    }
}