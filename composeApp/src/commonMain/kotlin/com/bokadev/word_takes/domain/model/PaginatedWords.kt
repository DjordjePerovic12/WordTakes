package com.bokadev.word_takes.domain.model

data class PaginatedWords(
    val items: List<WordItem>,
    val meta: PaginationMeta,
    val links: PaginationLinks
)