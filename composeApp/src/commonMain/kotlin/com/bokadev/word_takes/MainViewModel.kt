package com.bokadev.word_takes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bokadev.word_takes.domain.repository.DataStoreRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val evetChannel = Channel<MainEvent>()
    val events = evetChannel.receiveAsFlow()

    private var hasLoadedInitialData = false
    private val _state = MutableStateFlow(MainState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeSession()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MainState()
        )


    private var previousRefreshToken: String? = null

    init {
        viewModelScope.launch {
            val authInfo = dataStoreRepository.observeAuthInfo().firstOrNull()
            _state.update {
                it.copy(
                    isCheckingAuth = false,
                    isLoggedIn = authInfo != null
                )
            }
        }
    }


    private fun observeSession() {
        dataStoreRepository
            .observeAuthInfo()
            .onEach { authInfo ->
                val isSessionExpired = previousRefreshToken != null
                if (isSessionExpired) {
                    dataStoreRepository.set(null)
                    _state.update {
                        it.copy(
                            isLoggedIn = false
                        )
                    }
                    evetChannel.send(MainEvent.OnSessionExpired)
                }
            }
            .launchIn(viewModelScope)
    }

}