package com.bokadev.word_takes.presentation.single_user

import com.bokadev.word_takes.domain.model.Rating
import com.bokadev.word_takes.domain.model.Reactions
import com.bokadev.word_takes.domain.model.User
import com.bokadev.word_takes.domain.model.WordItem

data class SingleUserWordsState(
    val currentUserId: Int? = null,
    val items: List<WordItem> = emptyList(),
    val user: User? = null,

    val currentPage: Int = 0,
    val lastPage: Int = 1,

    val perPage: Int = 20,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoadingNextPage: Boolean = false,
    val error: String? = null,
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


    val canLoadMoreRatings: Boolean
        get() = ratingsCurrentPage < ratingsLastPage
}
