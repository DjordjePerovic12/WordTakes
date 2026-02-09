package com.bokadev.word_takes.core.networking

import com.bokadev.word_takes.data.remote.dto.ErrorResponseDto
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

fun String.addParams(params: Map<String, String>): String {
    if (params.isEmpty()) return this

    val result = params.entries.fold(this) { acc, (key, value) ->
        acc.replace("{$key}", value)
    }

    return result
}


fun String.addQueryParams(params: Map<String, String>): String {
    if (params.isEmpty()) return this

    val query = params.entries.joinToString("&") { (key, value) ->
        "${key}=${value}"
    }

    return "$this?$query"
}

suspend inline fun <reified T> responseToResource(
    response: HttpResponse
): Resource<T, NetworkError, String?> {
    return try {
        val errorMessage = runCatching {
            response.body<ErrorResponseDto>()?.message
        }.getOrNull()

        when (response.status.value) {
            in 200..299 -> {
                try {
                    val body = response.body<T>()
                    Resource.Success(body)
                } catch (e: Exception) {
                    Resource.Error(NetworkError.SERIALIZATION, e.message)
                }
            }
            400 -> Resource.Error(NetworkError.BAD_REQUEST, errorMessage)
            401 -> Resource.Error(NetworkError.UNAUTHORIZED, errorMessage)
            403 -> Resource.Error(NetworkError.FORBIDDEN, errorMessage)
            404 -> Resource.Error(NetworkError.NOT_FOUND, errorMessage)
            408 -> Resource.Error(NetworkError.REQUEST_TIMEOUT, errorMessage)
            429 -> Resource.Error(NetworkError.TOO_MANY_REQUESTS, errorMessage)
            in 500..599 -> Resource.Error(NetworkError.SERVER_ERROR, errorMessage)
            else -> Resource.Error(NetworkError.UNKNOWN, errorMessage)
        }
    } catch (e: Exception) {
        Resource.Error(NetworkError.NO_INTERNET, e.message)
    }
}

inline fun <T, E: DomainError, M: String?, R> Resource<T, E, M>.map(map: (T) -> R): Resource<R, E, M> {
    return when(this) {
        is Resource.Error -> Resource.Error(error, message)
        is Resource.Success -> Resource.Success(map(data))
    }
}

inline fun <T, E: DomainError, M: String?> Resource<T, E, M>.onSuccess(action: (T) -> Unit): Resource<T, E, M> {
    return when(this) {
        is Resource.Error -> this
        is Resource.Success -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: DomainError> Resource<T, E, String?>.onError(action: (E, String?) -> Unit): Resource<T, E, String?> {
    return when(this) {
        is Resource.Error -> {
            action(error, message)
            this
        }
        is Resource.Success -> this
    }
}