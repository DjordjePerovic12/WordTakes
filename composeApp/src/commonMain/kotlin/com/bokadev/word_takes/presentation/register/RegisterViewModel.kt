package com.bokadev.word_takes.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bokadev.word_takes.core.navigation.Screen
import com.bokadev.word_takes.core.navigation.utils.Navigator
import com.bokadev.word_takes.core.networking.onError
import com.bokadev.word_takes.core.networking.onSuccess
import com.bokadev.word_takes.core.utils.EmailValidator
import com.bokadev.word_takes.data.remote.dto.LoginRequestDto
import com.bokadev.word_takes.data.remote.dto.RegisterRequestDto
import com.bokadev.word_takes.domain.repository.AuthRepository
import com.bokadev.word_takes.domain.repository.DataStoreRepository
import com.bokadev.word_takes.presentation.login.LoginEvent
import com.bokadev.word_takes.presentation.login.LoginState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val navigator: Navigator
) : ViewModel() {


    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(RegisterState())

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


    private val isNameValidFlow = state
        .map { it.name.text }
        .map { name ->
            name.isNotBlank() && name.length >= 3
        }
        .distinctUntilChanged()
    private val isPasswordValidFlow = state
        .map { it.password.text }
        .map { password ->
            password.isNotBlank()
        }
        .distinctUntilChanged()

    private fun observeTextStates() {
        combine(
            isNameValidFlow,
            isEmailValidFlow,
            isPasswordValidFlow
        ) { isNameValid, isEmailValid, isPasswordValid ->
            _state.update {
                it.copy(
                    shouldEnableButton = isNameValid && isEmailValid && isPasswordValid,
                    isEmailValid = isEmailValid,
                    isPasswordValid = isPasswordValid
                )
            }
        }.launchIn(viewModelScope)
    }


    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnNameChange -> {
                _state.update {
                    it.copy(
                        name = event.name, nameError = null
                    )
                }
            }

            is RegisterEvent.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.email, emailError = null
                    )
                }
            }

            is RegisterEvent.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = event.password, passwordError = null
                    )
                }
            }

            is RegisterEvent.TogglePasswordVisibility -> {
                _state.update {
                    it.copy(isPasswordVisible = event.isVisible)
                }
            }

            RegisterEvent.OnRegisterClick -> {
                executeRegister()
            }
        }
    }

    private fun executeRegister() {
        viewModelScope.launch {
            authRepository.register(
                RegisterRequestDto(
                    name = state.value.name.text,
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