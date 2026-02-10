package com.bokadev.word_takes.domain.model

data class LoginResponse(
    val user: User,
    val token: String
)
