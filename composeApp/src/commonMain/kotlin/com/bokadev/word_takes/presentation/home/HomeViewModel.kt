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
    private val _state = MutableStateFlow(HomeState())

    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeWordValidation()
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
                _state.update {
                    it.copy(
                        isSubmitInProgress = false,
                        myWord = TextFieldValue()
                    )
                }

                _snackBarChannel.send("Word posted successfully")
            }.onError { networkError, e ->
                _state.update {
                    it.copy(isSubmitInProgress = false)
                }
                Napier.v("Failed login $networkError, $e")
            }
        }
    }
}