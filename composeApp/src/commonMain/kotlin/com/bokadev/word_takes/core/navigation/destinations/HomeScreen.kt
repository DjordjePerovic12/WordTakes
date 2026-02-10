package com.bokadev.word_takes.core.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bokadev.word_takes.core.navigation.Screen
import com.bokadev.word_takes.presentation.home.HomeScreen
import com.bokadev.word_takes.presentation.login.LoginScreen

fun NavGraphBuilder.homeScreenComposable(
    showSnackBar : (String) -> Unit
){
    composable<Screen.HomeScreen> {
        HomeScreen(
            showSnackBar = showSnackBar
        )
    }
}