package com.bokadev.word_takes.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bokadev.word_takes.data.remote.services.KtorApiService
import kotlinx.coroutines.launch


class LoginViewModel(
    private val ktorApiService: KtorApiService
) : ViewModel() {
    init {
        viewModelScope.launch {
            ktorApiService.test()
        }
    }
}