package com.bokadev.word_takes.presentation.home


import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bokadev.word_takes.core.navigation.Screen
import com.bokadev.word_takes.core.navigation.utils.Navigator
import com.bokadev.word_takes.core.networking.onError
import com.bokadev.word_takes.core.networking.onSuccess
import com.bokadev.word_takes.data.remote.dto.LoginRequestDto
import com.bokadev.word_takes.data.remote.dto.PostTakeRequestDto
import com.bokadev.word_takes.domain.repository.DataStoreRepository
import com.bokadev.word_takes.domain.repository.WordsRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeViewModel(
    private val wordsRepository: WordsRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val navigator: Navigator
) : ViewModel() {


    private var hasLoadedInitialData = false


    private val _snackBarChannel = Channel<String>()
    val snackBarChannel = _snackBarChannel.receiveAsFlow()


    private var didLoadFirstPage = false

    private var isRequestingNextPage = false
    private var lastRequestedPage: Int? = 0
    private val _state = MutableStateFlow(HomeState())

    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeWordValidation()
                executeGetWordsFirstPage()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = _state.value
        )

    private val isWordValidFlow = state
        .map { it.myWord.text.trim() }
        .distinctUntilChanged()
        .map { it.length >= 3 }
        .distinctUntilChanged()


    private fun observeWordValidation() {
        isWordValidFlow
            .onEach { isValid ->
                _state.update {
                    it.copy(
                        shouldEnableSubmitButton = isValid,
                        isWordValid = isValid
                    )
                }
            }
            .launchIn(viewModelScope)
    }


    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnSubmitClick -> {
                executePostTake()
            }

            is HomeEvent.OnWordChange -> {
                _state.update {
                    it.copy(myWord = event.word)
                }
            }

            HomeEvent.Refresh -> refreshFirstPage()
            HomeEvent.LoadNextPage -> executeGetWordsNextPage()
        }
    }

    private fun executePostTake() {
        _state.update {
            it.copy(isSubmitInProgress = true)
        }
        viewModelScope.launch {
            wordsRepository.postTake(
                PostTakeRequestDto(
                    state.value.myWord.text
                )
            ).onSuccess {
                refreshFirstPage()
                _state.update {
                    it.copy(
                        isSubmitInProgress = false,
                        myWord = TextFieldValue()
                    )
                }

                _snackBarChannel.send("Word posted successfully")
            }.onError { networkError, message ->
                _snackBarChannel.send(message ?: networkError.name)
                _state.update {
                    it.copy(isSubmitInProgress = false)
                }

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
                    lastPage = 1,
                    error = null
                )
            }

            wordsRepository.getAllWords(page = 1, perPage = state.value.perPage)
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

                wordsRepository.getAllWords(page = nextPage, perPage = s.perPage)
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


}