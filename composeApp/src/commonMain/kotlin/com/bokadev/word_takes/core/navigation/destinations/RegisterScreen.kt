package com.bokadev.word_takes.core.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bokadev.word_takes.core.navigation.Screen
import com.bokadev.word_takes.presentation.login.LoginScreen
import com.bokadev.word_takes.presentation.register.RegisterScreen

fun NavGraphBuilder.registerScreenComposable(
    showSnackBar: (String) -> Unit
) {
    composable<Screen.RegisterScreen> {
        RegisterScreen(
            showSnackBar = showSnackBar
        )
    }
}