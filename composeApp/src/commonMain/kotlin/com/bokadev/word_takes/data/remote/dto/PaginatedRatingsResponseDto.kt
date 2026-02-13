package com.bokadev.word_takes.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginatedRatingsResponseDto(
    @SerialName("word_id")
    val wordId: Long,

    val totals: ReactionsDto,

    val data: List<RatingResponseDto>,

    val meta: PaginationMetaDto
)
