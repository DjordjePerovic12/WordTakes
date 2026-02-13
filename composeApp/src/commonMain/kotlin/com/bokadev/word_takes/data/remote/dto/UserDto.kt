package com.bokadev.word_takes.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val name: String,
    val email: String?
)