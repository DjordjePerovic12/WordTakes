package com.bokadev.word_takes.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PaginationLinksDto(
    val first: String? = null,
    val last: String? = null,
    val prev: String? = null,
    val next: String? = null
)
