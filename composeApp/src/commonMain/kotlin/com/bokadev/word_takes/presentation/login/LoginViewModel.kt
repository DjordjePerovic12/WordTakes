package com.bokadev.word_takes.presentation.login

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bokadev.word_takes.core.navigation.Screen
import com.bokadev.word_takes.core.navigation.utils.Navigator
import com.bokadev.word_takes.core.networking.onError
import com.bokadev.word_takes.core.networking.onSuccess
import com.bokadev.word_takes.core.utils.EmailValidator
import com.bokadev.word_takes.data.remote.dto.LoginRequestDto
import com.bokadev.word_takes.data.remote.services.KtorApiService
import com.bokadev.word_takes.domain.repository.AuthRepository
import com.bokadev.word_takes.domain.repository.DataStoreRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val navigator: Navigator
) : ViewModel() {


    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(LoginState())

    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeTextStates()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = _state.value
        )

    private val isEmailValidFlow = state
        .map { email -> EmailValidator.validate(email.email.text) }
        .distinctUntilChanged()

    private val isPasswordValidFlow = state
        .map { it.password.text }
        .map { password ->
            password.isNotBlank()
        }
        .distinctUntilChanged()

    private fun observeTextStates() {
        combine(
            isEmailValidFlow,
            isPasswordValidFlow
        ) { isEmailValid, isPasswordValid ->
            _state.update {
                it.copy(
                    shouldEnableButton = isEmailValid && isPasswordValid,
                    isEmailValid = isEmailValid,
                    isPasswordValid = isPasswordValid
                )
            }
        }.launchIn(viewModelScope)
    }


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLoginClick -> {
                executeLogin()
            }

            is LoginEvent.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.email, emailError = null
                    )
                }
            }

            is LoginEvent.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = event.password, passwordError = null
                    )
                }
            }

            is LoginEvent.TogglePasswordVisibility -> {
                _state.update {
                    it.copy(isPasswordVisible = event.isVisible)
                }
            }

            LoginEvent.OnRegisterClick -> {

            }
        }
    }

    private fun executeLogin() {
        viewModelScope.launch {
            authRepository.login(
                LoginRequestDto(
                    email = state.value.email.text,
                    password = state.value.password.text
                )
            ).onSuccess {
                Napier.v("Successful login ${it.token}")
                dataStoreRepository.set(it)
                navigator.navigateTo(Screen.HomeScreen)

            }
                .onError { networkError, e ->
                    Napier.v("Failed login $networkError, $e")
                }
        }
    }


}