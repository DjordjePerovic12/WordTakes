package com.bokadev.word_takes.core.navigation

import kotlinx.serialization.Serializable


sealed interface Screen {

    @Serializable
    data object ROOT : Screen

    @Serializable
    data object LoginScreen : Screen

    @Serializable
    data object RegisterScreen : Screen

    @Serializable
    data object HomeScreen : Screen

    @Serializable
    data object LobbyCreateScreen : Screen

    @Serializable
    data class LobbyScreen(val lobbyId : String) : Screen
}