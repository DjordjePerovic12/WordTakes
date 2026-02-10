package com.bokadev.word_takes.presentation.login

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.text.input.TextFieldValue

sealed class LoginEvent {
    data class OnPasswordChange(val password: TextFieldValue) : LoginEvent()
    data class OnEmailChange(val email: TextFieldValue) : LoginEvent()
    data object OnRegisterClick : LoginEvent()
    data object OnLoginClick : LoginEvent()
    data class TogglePasswordVisibility(val isVisible: Boolean) : LoginEvent()
}