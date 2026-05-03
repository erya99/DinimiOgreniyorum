package com.dinimiogreniyorum.app.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dinimiogreniyorum.app.QuestionRepository
import com.dinimiogreniyorum.app.QuizViewModel
import com.dinimiogreniyorum.app.SeedData
import com.dinimiogreniyorum.db.DatabaseDriverFactory
import com.dinimiogreniyorum.db.createDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = createDatabase(DatabaseDriverFactory(this))
        val repository = QuestionRepository(database)
        val viewModel = QuizViewModel(repository)
        SeedData(repository).seedIfEmpty()

        setContent {
            DinimOgreniyorumTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}