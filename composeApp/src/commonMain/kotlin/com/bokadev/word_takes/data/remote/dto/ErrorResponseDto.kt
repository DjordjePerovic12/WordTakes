package com.bokadev.word_takes.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable

data class ErrorResponseDto(
    val message: String?
)
