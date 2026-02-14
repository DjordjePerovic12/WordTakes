package com.bokadev.word_takes

sealed interface MainEvent {
    data object OnSessionExpired: MainEvent
    data object OnProfileClick: MainEvent
}