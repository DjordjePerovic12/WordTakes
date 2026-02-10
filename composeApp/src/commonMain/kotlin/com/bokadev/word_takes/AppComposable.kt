package com.bokadev.word_takes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bokadev.word_takes.core.navigation.BaseAppNavigation
import com.bokadev.word_takes.core.navigation.Screen
import com.bokadev.word_takes.core.navigation.utils.Navigator
import com.bokadev.word_takes.core.navigation.utils.ObserveAsEvents
import com.bokadev.word_takes.core.utils.CustomModifiers
import com.bokadev.word_takes.core.utils.rememberAppState
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun AppComposable(
    viewModel: MainViewModel = koinViewModel()
) {
    val navigator = koinInject<Navigator>()

    val state by viewModel.state.collectAsStateWithLifecycle()

    val startDestination = if(state.isLoggedIn) Screen.HomeScreen else Screen.LoginScreen

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