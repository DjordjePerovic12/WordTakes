package com.bokadev.word_takes.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<S : BaseState, E : BaseEvent> : ViewModel() {
    protected abstract val initialState: S
    protected val state by lazy { MutableStateFlow(initialState) }
    val stateFlow: StateFlow<S> by lazy { state }

    protected val _snackBarChannel = Channel<String>()
    val snackBarChanel = _snackBarChannel.receiveAsFlow()


    abstract fun onEvent(event : E)
}