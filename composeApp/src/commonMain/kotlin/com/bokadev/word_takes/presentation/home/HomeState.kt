package com.bokadev.word_takes.presentation.home

import androidx.compose.ui.text.input.TextFieldValue

data class HomeState(
    val myWord: TextFieldValue = TextFieldValue(),
    val myWordError: String? = null,
    val isWordValid: Boolean = false,
    val shouldEnableSubmitButton: Boolean = false,
    val isSubmitInProgress: Boolean = false
)