package com.bokadev.word_takes.presentation.home

import androidx.compose.ui.text.input.TextFieldValue
import com.bokadev.word_takes.domain.model.Rating
import com.bokadev.word_takes.domain.model.Reactions
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
    val isRateInProgress: Boolean = false,

    val selectedWord: String = "",

    val shouldShowRatingsBottomSheet: Boolean = false,
    val isLoadingRatings: Boolean = false,

    val ratingsWordId: Int? = null,
    val ratingsItems: List<Rating> = emptyList(),
    val ratingsTotals: Reactions? = null,

    val ratingsCurrentPage: Int = 0,
    val ratingsLastPage: Int = 1,
    val ratingsPerPage: Int = 20,

    val isRatingsLoading: Boolean = false,
    val isRatingsLoadingNextPage: Boolean = false,
    val isRatingsRefreshing: Boolean = false
) {
    val canLoadMore: Boolean
        get() = currentPage < lastPage

    val canLoadMoreRatings: Boolean get() = ratingsCurrentPage < ratingsLastPage
}