package com.bokadev.word_takes

data class MainState(
    val isLoggedIn: Boolean = false,
    val isCheckingAuth: Boolean = true,
    val currentUserId: Int? = null
)
