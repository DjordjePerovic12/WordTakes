package com.bokadev.word_takes.presentation.register

import androidx.compose.ui.text.input.TextFieldValue
import com.bokadev.word_takes.presentation.login.LoginEvent

sealed class RegisterEvent {
    data class OnNameChange(val name: TextFieldValue) : RegisterEvent()
    data class OnPasswordChange(val password: TextFieldValue) : RegisterEvent()
    data class OnEmailChange(val email: TextFieldValue) : RegisterEvent()
    data object OnRegisterClick : RegisterEvent()
    data class TogglePasswordVisibility(val isVisible: Boolean) : RegisterEvent()
}