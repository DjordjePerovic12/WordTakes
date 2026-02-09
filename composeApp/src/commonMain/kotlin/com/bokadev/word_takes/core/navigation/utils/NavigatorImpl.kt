package com.bokadev.word_takes.core.navigation.utils

import androidx.navigation.NavOptionsBuilder
import com.bokadev.word_takes.core.navigation.Screen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class NavigatorImpl : Navigator {
    private val _navigationChannel = Channel<NavType>()
    override val navigationFlow = _navigationChannel.receiveAsFlow()


    override suspend fun navigateTo(
        destination: Screen,
        navOptions: NavOptionsBuilder.() -> Unit
    ) {
        _navigationChannel.send(
            NavType.NavigateToRoute(
                destination = destination,
                navOptions = navOptions
            )
        )
    }

    override suspend fun navigateUp() {
        _navigationChannel.send(NavType.NavigateUp)
    }
}