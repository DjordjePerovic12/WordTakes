package com.bokadev.word_takes.presentation.single_user

import androidx.compose.ui.text.input.TextFieldValue

sealed class SingleUserWordEvent {
    data object Refresh : SingleUserWordEvent()
    data object LoadNextPage : SingleUserWordEvent()

    data class OnRateWordClick(
        val wordId: Int,
        val reaction: String
    ) : SingleUserWordEvent()


    data class OnSeeRatingsClick(val selectedWord: String) : SingleUserWordEvent()
    data class OpenBottomSheet(val wordId: Int, val selectedWord: String) : SingleUserWordEvent()

    data object DismissBottomSheet : SingleUserWordEvent()

    data object LoadRatingsNextPage : SingleUserWordEvent()
}