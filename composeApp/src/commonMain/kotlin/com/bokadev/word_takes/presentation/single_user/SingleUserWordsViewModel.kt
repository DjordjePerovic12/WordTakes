package com.bokadev.word_takes.presentation.single_user


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


class SingleUserWordsViewModel(
    private val wordsRepository: WordsRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val navigator: Navigator,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {


    val route: Screen.SingleUserWordsScreen = savedStateHandle.toRoute()

    val userId: Int = route.userId


    private var hasLoadedInitialData = false


    private val _snackBarChannel = Channel<String>()
    val snackBarChannel = _snackBarChannel.receiveAsFlow()


    private var didLoadFirstPage = false

    private var isRequestingNextPage = false
    private var lastRequestedPage: Int? = 0


    private var isRequestingRatingsNextPage = false
    private var lastRequestedRatingsPage: Int? = 0
    private val _state = MutableStateFlow(SingleUserWordsState())


    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeSession()
                executeGetWordsFirstPage()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = _state.value
        )


    fun onEvent(event: SingleUserWordEvent) {
        when (event) {
            SingleUserWordEvent.Refresh -> refreshFirstPage()
            SingleUserWordEvent.LoadNextPage -> executeGetWordsNextPage()

            is SingleUserWordEvent.OnRateWordClick -> {
                executeRateWord(
                    wordId = event.wordId,
                    reaction = event.reaction
                )
            }

            is SingleUserWordEvent.OnSeeRatingsClick -> {
                _state.update {
                    it.copy(selectedWord = event.selectedWord)
                }
            }

            is SingleUserWordEvent.OpenBottomSheet -> {
                _state.update {
                    it.copy(
                        shouldShowRatingsBottomSheet = true,
                        selectedWord = event.selectedWord
                    )
                }

                executeGetRatingsFirstPage(wordId = event.wordId)
            }


            SingleUserWordEvent.DismissBottomSheet -> {
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


            is SingleUserWordEvent.LoadRatingsNextPage -> {
                executeGetRatingsNextPage()
            }
        }
    }

    fun refreshFirstPage() = executeGetWordsFirstPage(force = true)
    private fun executeGetWordsFirstPage(force: Boolean = false) {
        // same logic as Outlet: reset paging flags
        isRequestingNextPage = false
        lastRequestedPage = 0

        if (!force && didLoadFirstPage) return
        didLoadFirstPage = true

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = it.items.isEmpty(),
                    isRefreshing = force && it.items.isNotEmpty(),
                    isLoadingNextPage = false,
                    items = emptyList(),
                    currentPage = 0,
                    lastPage = 0,
                    error = null
                )
            }

            wordsRepository.getWordsByUserId(
                userId = userId,
                page = 1,
                perPage = state.value.perPage
            )
                .onSuccess { response ->
                    _state.update { s ->
                        // If you later add IDs, prefer distinctBy { it.id }
                        val merged = response.items // first page overwrite
                        s.copy(
                            items = merged,
                            currentPage = response.meta.currentPage,
                            lastPage = response.meta.lastPage,
                            isLoading = false,
                            isRefreshing = false,
                            isLoadingNextPage = false,
                            error = null
                        )
                    }
                }
                .onError { networkError, message ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            isLoadingNextPage = false
                        )
                    }
                    _snackBarChannel.send(message ?: networkError.name)
                }
        }
    }

    fun executeGetWordsNextPage() {
        val s = state.value
        if (s.isLoading || s.isLoadingNextPage || !s.canLoadMore) return

        val nextPage = s.currentPage + 1
        if (isRequestingNextPage) return
        if (nextPage == lastRequestedPage) return

        isRequestingNextPage = true
        lastRequestedPage = nextPage

        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoadingNextPage = true, error = null) }

                wordsRepository.getWordsByUserId(userId = userId, page = nextPage, perPage = s.perPage)
                    .onSuccess { response ->
                        _state.update { current ->
                            // append, and (optionally) dedupe
                            val appended = current.items + response.items
                            current.copy(
                                items = appended,
                                currentPage = response.meta.currentPage,
                                lastPage = response.meta.lastPage,
                                isLoadingNextPage = false,
                                error = null
                            )
                        }
                    }
                    .onError { networkError, message ->
                        _state.update { it.copy(isLoadingNextPage = false) }
                        _snackBarChannel.send(message ?: networkError.name)
                    }
            } finally {
                isRequestingNextPage = false // ✅ always reset
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
                refreshFirstPage()
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

        // 🔄 reset paging EVERY TIME
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

    private fun observeSession() {
        dataStoreRepository
            .observeAuthInfo()
            .onEach { authInfo ->
                _state.update {
                    it.copy(currentUserId = authInfo?.user?.id)
                }
            }
            .launchIn(viewModelScope)
    }


}