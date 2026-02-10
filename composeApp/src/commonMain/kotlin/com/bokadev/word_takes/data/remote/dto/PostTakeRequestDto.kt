package com.bokadev.word_takes.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostTakeRequestDto(
    val word: String
)
