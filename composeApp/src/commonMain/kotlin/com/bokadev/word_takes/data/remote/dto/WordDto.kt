package com.bokadev.word_takes.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WordDto(
    val id: Int,
    val name: String,
    @SerialName("created_at")
    val createdAt: String,
    val word: String,
    val reactions: ReactionsDto,
    @SerialName("my_reaction") val myReaction: String?
)
