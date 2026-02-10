package com.bokadev.word_takes.domain.model

data class PaginationLinks(
    val first: String? = null,
    val last: String? = null,
    val prev: String? = null,
    val next: String? = null
)