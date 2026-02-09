package com.bokadev.word_takes.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bokadev.word_takes.core.navigation.destinations.loginComposable
import com.bokadev.word_takes.core.navigation.utils.NavType
import com.bokadev.word_takes.core.navigation.utils.Navigator
import com.bokadev.word_takes.core.navigation.utils.ObserveAsEvents
import io.github.aakira.napier.Napier

@Composable
fun BaseAppNavigation(
    navController: NavHostController,
    navigator: Navigator,
    startDestination: Screen,
    showSnackBar: (String) -> Unit
) {
    ObserveAsEvents(flow = navigator.navigationFlow) { navType ->
        Napier.v("NAVTYPE $navType")
        when (navType) {
            is NavType.NavigateToRoute -> {
                navController.navigate(route = navType.destination) {
                    launchSingleTop = true
                    restoreState = true
                    navType.navOptions(this)
                }
            }

            NavType.NavigateUp -> {
                if (navController.previousBackStackEntry != null) {
                    navController.navigateUp()
                }
            }
        }
    }

    NavHost(
        route = Screen.ROOT::class,
        startDestination = startDestination,
        navController = navController
    ) {
        loginComposable(showSnackBar = showSnackBar)
    }
}