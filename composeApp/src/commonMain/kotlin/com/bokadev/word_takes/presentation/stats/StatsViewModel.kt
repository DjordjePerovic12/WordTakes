package com.bokadev.word_takes.presentation.stats


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.bokadev.word_takes.core.navigation.Screen
import com.bokadev.word_takes.core.navigation.utils.Navigator
import com.bokadev.word_takes.core.networking.onError
import com.bokadev.word_takes.core.networking.onSuccess
import com.bokadev.word_takes.data.remote.dto.RateWordRequestDto
import com.bokadev.word_takes.domain.repository.DataStoreRepository
import com.bokadev.word_takes.domain.repository.WordsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class StatsViewModel(
    private val wordsRepository: WordsRepository,
    private val navigator: Navigator,
) : ViewModel() {

    private var hasLoadedInitialData = false


    private val _snackBarChannel = Channel<String>()
    val snackBarChannel = _snackBarChannel.receiveAsFlow()
    private var isRequestingRatingsNextPage = false
    private var lastRequestedRatingsPage: Int? = 0
    private val _state = MutableStateFlow(StatsState())


    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                executeGetStats()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = _state.value
        )


    fun onEvent(event: StatsEvent) {
        when (event) {
            StatsEvent.Refresh -> executeGetStats()


            is StatsEvent.OnRateWordClick -> {
                executeRateWord(
                    wordId = event.wordId,
                    reaction = event.reaction
                )
            }

            is StatsEvent.OnSeeRatingsClick -> {
                _state.update {
                    it.copy(selectedWord = event.selectedWord)
                }
            }

            is StatsEvent.OpenBottomSheet -> {
                _state.update {
                    it.copy(
                        shouldShowRatingsBottomSheet = true,
                        selectedWord = event.selectedWord
                    )
                }

                executeGetRatingsFirstPage(wordId = event.wordId)
            }


            StatsEvent.DismissBottomSheet -> {
                _state.update {
                    it.copy(
                        selectedWord = "",
                        ratingsWordId = null,
                        ratingsItems = emptyList(),
                        ratingsTotals = null,
                        shouldShowRatingsBottomSheet = false
                    )
                }
            }


            is StatsEvent.LoadRatingsNextPage -> {
                executeGetRatingsNextPage()
            }
        }
    }


    private fun executeGetStats() {
        viewModelScope.launch {
            wordsRepository.getStats().onSuccess { response ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        items = response
                    )
                }
            }.onError { networkError, message ->
                _snackBarChannel.send(message ?: networkError.name)
                _state.update {
                    it.copy(isLoading = false)
                }

            }
        }
    }

    private fun executeRateWord(wordId: Int, reaction: String) {
        _state.update {
            it.copy(isRateInProgress = true)
        }
        viewModelScope.launch {
            wordsRepository.rateWord(
                wordId = wordId,
                body = RateWordRequestDto(
                    reaction = reaction
                )
            ).onSuccess {
                executeGetStats()
                _state.update {
                    it.copy(
                        isRateInProgress = false,
                    )
                }

                _snackBarChannel.send("Word posted successfully")
            }.onError { networkError, message ->
                _snackBarChannel.send(message ?: networkError.name)
                _state.update {
                    it.copy(isRateInProgress = false)
                }

            }
        }
    }


    private fun executeGetRatingsFirstPage(wordId: Int) {
        isRequestingRatingsNextPage = false
        lastRequestedRatingsPage = 0

        viewModelScope.launch {
            _state.update {
                it.copy(
                    ratingsWordId = wordId,
                    ratingsItems = emptyList(),
                    ratingsTotals = null,
                    ratingsCurrentPage = 0,
                    ratingsLastPage = 1,
                    isRatingsLoading = true,
                    isRatingsRefreshing = false,
                    isRatingsLoadingNextPage = false
                )
            }

            wordsRepository.getAllRatings(
                wordId = wordId,
                page = 1,
                perPage = state.value.ratingsPerPage
            )
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            ratingsItems = response.items,
                            ratingsTotals = response.reactions,
                            ratingsCurrentPage = response.meta.currentPage,
                            ratingsLastPage = response.meta.lastPage,
                            isRatingsLoading = false
                        )
                    }
                }
                .onError { networkError, message ->
                    _state.update {
                        it.copy(isRatingsLoading = false)
                    }
                    _snackBarChannel.send(message ?: networkError.name)
                }
        }
    }


    fun executeGetRatingsNextPage() {
        val s = state.value
        val wordId = s.ratingsWordId ?: return

        if (s.isRatingsLoading || s.isRatingsLoadingNextPage || !s.canLoadMoreRatings) return

        val nextPage = s.ratingsCurrentPage + 1
        if (isRequestingRatingsNextPage) return
        if (nextPage == lastRequestedRatingsPage) return

        isRequestingRatingsNextPage = true
        lastRequestedRatingsPage = nextPage

        viewModelScope.launch {
            try {
                _state.update { it.copy(isRatingsLoadingNextPage = true) }

                wordsRepository.getAllRatings(
                    wordId = wordId,
                    page = nextPage,
                    perPage = s.ratingsPerPage
                )
                    .onSuccess { response ->
                        _state.update { current ->
                            val appended = current.ratingsItems + response.items
                            current.copy(
                                ratingsItems = appended,
                                ratingsTotals = response.reactions, // keep updated
                                ratingsCurrentPage = response.meta.currentPage,
                                ratingsLastPage = response.meta.lastPage,
                                isRatingsLoadingNextPage = false
                            )
                        }
                    }
                    .onError { networkError, message ->
                        _state.update { it.copy(isRatingsLoadingNextPage = false) }
                        _snackBarChannel.send(message ?: networkError.name)
                    }
            } finally {
                isRequestingRatingsNextPage = false
            }
        }
    }
}