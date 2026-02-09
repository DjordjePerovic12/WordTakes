package com.bokadev.word_takes.core.networking

interface Error

enum class NetworkError : Error {
    BAD_REQUEST,
    UNAUTHORIZED,
    FORBIDDEN,
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER_ERROR,
    SERIALIZATION,
    NOT_FOUND,
    UNKNOWN
}