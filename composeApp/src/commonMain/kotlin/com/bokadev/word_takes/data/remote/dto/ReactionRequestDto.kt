package com.bokadev.word_takes.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
enum class ReactionRequestDto {
    GOOD,
    AMAZING,
    BAD,
    AWFUL
}