package com.bokadev.word_takes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import llc.amplitudo.cerovo.ui.theme.WordTakesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        var shouldShowSplashScreen = true
        installSplashScreen().setKeepOnScreenCondition {
            shouldShowSplashScreen
        }
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)
        setContent {
            WordTakesTheme {
                AppComposable(
                    onAuthenticationChecked = {
                        shouldShowSplashScreen = false
                    }
                )
            }
        }
    }
}