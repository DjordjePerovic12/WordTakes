package com.bokadev.word_takes.core.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bokadev.word_takes.core.navigation.Screen
import com.bokadev.word_takes.presentation.home.HomeScreen
import com.bokadev.word_takes.presentation.home.SingleUserWordsScreen

fun NavGraphBuilder.singleUserWordsScreen(
    showSnackBar: (String) -> Unit
) {
    composable<Screen.SingleUserWordsScreen> {
        SingleUserWordsScreen(
            showSnackBar = showSnackBar
        )
    }
}