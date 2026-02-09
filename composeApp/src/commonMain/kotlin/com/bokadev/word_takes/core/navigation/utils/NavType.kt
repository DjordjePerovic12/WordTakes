package com.bokadev.word_takes.core.navigation.utils

import androidx.navigation.NavOptionsBuilder
import com.bokadev.word_takes.core.navigation.Screen

sealed class NavType {
    data object NavigateUp : NavType()

    data class NavigateToRoute(
        val destination : Screen,
        val navOptions : NavOptionsBuilder.() -> Unit = {}
    ) : NavType()
}