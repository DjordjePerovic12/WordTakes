package com.bokadev.word_takes.presentation.register

import androidx.compose.ui.text.input.TextFieldValue

data class RegisterState(
    val name: TextFieldValue = TextFieldValue(),
    val email: TextFieldValue = TextFieldValue(),
    val password: TextFieldValue = TextFieldValue(),
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isNameValid: Boolean = false,
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val shouldEnableButton: Boolean = false
)
