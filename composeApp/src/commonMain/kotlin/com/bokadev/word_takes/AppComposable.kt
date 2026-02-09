package com.bokadev.word_takes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bokadev.word_takes.core.navigation.BaseAppNavigation
import com.bokadev.word_takes.core.navigation.Screen
import com.bokadev.word_takes.core.navigation.utils.Navigator
import com.bokadev.word_takes.core.utils.CustomModifiers
import com.bokadev.word_takes.core.utils.rememberAppState
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun AppComposable() {
    val navigator = koinInject<Navigator>()
    val startDestination = Screen.LoginScreen
    MaterialTheme {
        val appState = rememberAppState()

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
            snackbarHost = {
                CustomModifiers.snackBarHost(appState.scaffoldState)
            }
        ) {
            BaseAppNavigation(
                navigator = navigator,
                navController = appState.navController,
                startDestination = startDestination,
                showSnackBar = { message ->
                    Napier.v(message, null, "SNACKBAR")
                    appState.showSnackBar(message)
                }
            )
        }
    }
}