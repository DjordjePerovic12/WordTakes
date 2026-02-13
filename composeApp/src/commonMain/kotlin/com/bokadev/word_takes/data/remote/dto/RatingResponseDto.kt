package com.bokadev.word_takes.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingResponseDto(
    val user: UserDto,
    val reaction: String,

    @SerialName("created_at")
    val createdAt: String
)
