package com.bokadev.word_takes.presentation.home

import androidx.compose.ui.text.input.TextFieldValue
import com.bokadev.word_takes.domain.model.WordItem

data class HomeState(
    val myWord: TextFieldValue = TextFieldValue(),
    val myWordError: String? = null,
    val isWordValid: Boolean = false,
    val shouldEnableSubmitButton: Boolean = false,
    val isSubmitInProgress: Boolean = false,

    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoadingNextPage: Boolean = false,
    val error: String? = null,

    val items: List<WordItem> = emptyList(),

    val currentPage: Int = 0,
    val lastPage: Int = 1,

    val perPage: Int = 20,
    val isRateInProgress: Boolean = false
) {
    val canLoadMore: Boolean
        get() = currentPage < lastPage
}