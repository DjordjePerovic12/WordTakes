package com.bokadev.word_takes.presentation.home

import androidx.compose.ui.text.input.TextFieldValue


sealed class HomeEvent {
    data class OnWordChange(val word: TextFieldValue) : HomeEvent()
    data object OnSubmitClick : HomeEvent()

    data object Refresh : HomeEvent()
    data object LoadNextPage : HomeEvent()

    data class OnRateWordClick(
        val wordId: Int,
        val reaction: String
    ) : HomeEvent()
}