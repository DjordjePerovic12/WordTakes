package com.bokadev.word_takes.domain.model



data class PaginatedRatings(
    val wordId: Long,
    val reactions: Reactions,
    val items: List<Rating>,
    val meta: PaginationMeta
)
