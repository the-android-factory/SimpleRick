package com.androidfactory.simplerick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.androidfactory.network.KtorClient
import com.androidfactory.simplerick.screens.CharacterDetailsScreen
import com.androidfactory.simplerick.ui.theme.RickPrimary
import com.androidfactory.simplerick.ui.theme.SimpleRickTheme

class MainActivity : ComponentActivity() {

    private val ktorClient = KtorClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleRickTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = RickPrimary
                ) {
                    CharacterDetailsScreen(
                        ktorClient = ktorClient,
                        characterId = 1
                    )
                }
            }
        }
    }
}