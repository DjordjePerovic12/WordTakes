package com.bokadev.word_takes.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class StatsResponseDto(
    val items: List<WordDto>
)
