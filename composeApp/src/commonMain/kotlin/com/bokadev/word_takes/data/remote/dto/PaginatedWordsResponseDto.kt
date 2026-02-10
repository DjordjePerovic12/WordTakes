package com.bokadev.word_takes.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WordsPageResponseDto(
    @SerialName("current_page")
    val currentPage: Int,
    val data: List<WordDto>,

    @SerialName("first_page_url")
    val firstPageUrl: String? = null,

    val from: Int? = null,

    @SerialName("last_page")
    val lastPage: Int,

    @SerialName("last_page_url")
    val lastPageUrl: String? = null,

    // IMPORTANT: this is a LIST in your backend response
    val links: List<PageLinkDto> = emptyList(),

    @SerialName("next_page_url")
    val nextPageUrl: String? = null,

    val path: String? = null,

    @SerialName("per_page")
    val perPage: Int,

    @SerialName("prev_page_url")
    val prevPageUrl: String? = null,

    val to: Int? = null,
    val total: Int
)

@Serializable
data class PageLinkDto(
    val url: String? = null,
    val label: String,
    val page: Int? = null,
    val active: Boolean
)
