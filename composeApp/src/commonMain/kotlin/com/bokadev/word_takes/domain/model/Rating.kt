package com.bokadev.word_takes.domain.model

import kotlin.time.Instant

data class Rating(
    val user: User,
    val reaction: String,
    val createdAt: Instant
)