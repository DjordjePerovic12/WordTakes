package com.bokadev.word_takes.core.networking

typealias DomainError = Error

sealed interface Resource<out D, out E : DomainError, out M : String?> {
    data class Success<out D>(val data: D) : Resource<D, Nothing, Nothing>
    data class Error<out E : DomainError, out M : String?>(val error: E, val message: String?) :
        Resource<Nothing, E, M>
}