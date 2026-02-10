package com.bokadev.word_takes

import androidx.compose.ui.window.ComposeUIViewController
import llc.amplitudo.cerovo.ui.theme.WordTakesTheme

fun MainViewController() = ComposeUIViewController {
    WordTakesTheme {
        AppComposable()
    }
}