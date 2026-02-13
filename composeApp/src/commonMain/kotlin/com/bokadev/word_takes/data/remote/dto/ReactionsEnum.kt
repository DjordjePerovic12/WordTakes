package com.bokadev.word_takes.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
enum class ReactionsEnum {
    GOOD,
    AMAZING,
    BAD,
    AWFUL,
    SKIPPED
}