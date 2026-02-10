package com.bokadev.word_takes.presentation.login

import androidx.compose.ui.text.input.TextFieldValue

data class LoginState(
    val email: TextFieldValue = TextFieldValue(),
    val password: TextFieldValue = TextFieldValue(),
    val emailError: String? = null,
    val passwordError: String? = null,
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val shouldEnableButton: Boolean = false
)
