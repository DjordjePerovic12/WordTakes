package com.bokadev.word_takes.domain.model

data class WordItem(
    val id: Int,
    val user: User,
    val createdAtIso: String, // keep ISO string for now; we can convert to Instant later in KMP safely
    val word: String,
    val reactions: Reactions,
    val myReaction: String?,
)