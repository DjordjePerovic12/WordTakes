package com.bokadev.word_takes.core.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bokadev.word_takes.core.navigation.Screen
import com.bokadev.word_takes.presentation.login.LoginScreen

fun NavGraphBuilder.loginComposable(
    showSnackBar : (String) -> Unit
){
    composable<Screen.LoginScreen> {
        LoginScreen(
            showSnackBar = showSnackBar
        )
    }
}