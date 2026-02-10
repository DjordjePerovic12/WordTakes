package com.bokadev.word_takes.domain.model

data class AuthInfo(
    val user: User,
    val token: String
)
