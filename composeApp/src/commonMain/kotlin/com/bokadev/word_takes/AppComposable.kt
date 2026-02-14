package com.bokadev.word_takes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bokadev.word_takes.core.components.WordTakesBottombAR
import com.bokadev.word_takes.core.navigation.BaseAppNavigation
import com.bokadev.word_takes.core.navigation.Screen
import com.bokadev.word_takes.core.navigation.utils.Navigator
import com.bokadev.word_takes.core.navigation.utils.ObserveAsEvents
import com.bokadev.word_takes.core.utils.CustomModifiers
import com.bokadev.word_takes.core.utils.rememberAppState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun AppComposable(
    viewModel: MainViewModel = koinViewModel(),
    onAuthenticationChecked: () -> Unit = {}
) {
    val navigator = koinInject<Navigator>()

    val state by viewModel.state.collectAsStateWithLifecycle()

    val startDestination = if (state.isLoggedIn) Screen.HomeScreen else Screen.LoginScreen


    val scope = rememberCoroutineScope()

    LaunchedEffect(state.isCheckingAuth) {
        if (!state.isCheckingAuth) {
            onAuthenticationChecked()
        }
    }

    MaterialTheme {
        val appState = rememberAppState()
        val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
        var bottomBarState by remember { (mutableStateOf(false)) }
        var selectedIndex by remember { mutableStateOf(0) }

        bottomBarState = when {

            navBackStackEntry?.destination?.hasRoute<Screen.HomeScreen>() == true -> {
                selectedIndex = 0
                true
            }

            navBackStackEntry?.destination?.hasRoute<Screen.SingleUserWordsScreen>() == true -> {
                true
            }

            else -> false
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .navigationBarsPadding(),
            snackbarHost = {
                CustomModifiers.snackBarHost(appState.scaffoldState)
            },
            bottomBar = {
                if (bottomBarState) {
                    WordTakesBottombAR(
                        selected = selectedIndex,
                        navigateToHome = {
                            scope.launch {
                                // If we're already on MoreScreen, do nothing
                                val onHomeScreen =
                                    appState.navController.currentDestination?.hasRoute<Screen.HomeScreen>() == true
                                if (onHomeScreen) return@launch

                                // If MoreScreen exists in backstack (e.g. More -> Jobs), pop back to it
                                val popped = appState.navController.popBackStack(
                                    route = Screen.HomeScreen,
                                    inclusive = false
                                )

                                // If it wasn't in the back stack (e.g. process recreation), navigate to it
                                if (!popped) {
                                    navigator.navigateTo(Screen.HomeScreen) {
//                                            launchSingleTop = true
//                                            popUpTo(Screen.ROOT) { inclusive = false }
                                    }
                                }
                            }
                        },
                        navigateToStats = {
//                            scope.launch {
//                                navigator.navigateTo(Screen.CategoriesScreen) {
//                                    launchSingleTop = true
//
//                                    // Remove the existing CategoriesScreen entry (and its saved UI state)
//                                    popUpTo(Screen.CategoriesScreen) {
//                                        inclusive = true
//                                    }
//                                }
//                            }

                        },
                        navigateToProfile = {
                            selectedIndex = 2
                            viewModel.onEvent(MainEvent.OnProfileClick)
                        }
                    )
                }
            }
        ) {
            if (!state.isCheckingAuth)
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