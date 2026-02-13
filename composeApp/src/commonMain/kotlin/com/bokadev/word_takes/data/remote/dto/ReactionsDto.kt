package com.bokadev.word_takes.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class ReactionsDto(
    val good: Int,
    val amazing: Int,
    val bad: Int,
    val awful: Int,
    val skipped: Int
)
