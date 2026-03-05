package com.bokadev.word_takes.core.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bokadev.word_takes.core.navigation.Screen
import com.bokadev.word_takes.presentation.home.StatsScreen

fun NavGraphBuilder.statsScreenComposable(
    showSnackBar: (String) -> Unit
) {
    composable<Screen.StatsScreen> {
        StatsScreen(
            showSnackBar = showSnackBar
        )
    }
}