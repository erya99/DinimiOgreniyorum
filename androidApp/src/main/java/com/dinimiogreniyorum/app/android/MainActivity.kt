package com.dinimiogreniyorum.app.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dinimiogreniyorum.app.DailyViewModel
import com.dinimiogreniyorum.app.QuestionRepository
import com.dinimiogreniyorum.app.QuizViewModel
import com.dinimiogreniyorum.app.SeedData
import com.dinimiogreniyorum.app.StatsViewModel
import com.dinimiogreniyorum.db.DatabaseDriverFactory
import com.dinimiogreniyorum.db.createDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DailySelection tablosu yoksa oluştur
        val dbHelper = this.openOrCreateDatabase("dinimogreniyorum.db", MODE_PRIVATE, null)
        dbHelper.execSQL(
            """
            CREATE TABLE IF NOT EXISTS DailySelection (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                questionId INTEGER NOT NULL,
                selectedDate TEXT NOT NULL
            )
            """.trimIndent()
        )
        dbHelper.close()

        val database = createDatabase(DatabaseDriverFactory(this))
        val repository = QuestionRepository(database)
        val viewModel = QuizViewModel(repository)
        val statsViewModel = StatsViewModel(repository)
        val dailyViewModel = DailyViewModel(repository)
        SeedData(repository).seedIfEmpty()

        setContent {
            DinimOgreniyorumTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
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