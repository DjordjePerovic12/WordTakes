package com.bokadev.word_takes.data.remote

import com.bokadev.word_takes.BuildKonfig
import com.bokadev.word_takes.core.navigation.utils.Navigator
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.pingInterval
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

fun provideKtorClient(
//    localStorage : LocalStorageRepository,
    navigator: Navigator
): HttpClient {

    val client = HttpClient {
        engine {
            dispatcher = Dispatchers.IO
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
            socketTimeoutMillis = 30_000
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 3)
            exponentialDelay()
        }
        defaultRequest {
            url(BuildKonfig.BASE_URL) // Your base URL
            // You can also set default headers here
            header("Content-Type", "application/json")
        }
        install(WebSockets) {
            pingInterval = 20.seconds// Keep connection alive
            maxFrameSize = Long.MAX_VALUE
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.v(message, null, "HTTP Client")
                }
            }
            level = LogLevel.ALL // equivalent to BODY level
        }.also { Napier.base(DebugAntilog()) }
        install(Auth) {
            bearer {
                loadTokens {
                    // Load your tokens here
                    BearerTokens(
                        accessToken = "",//localStorage.getAccessToken(),
                        refreshToken = ""//localStorage.getRefreshToken()
                    )
                }

//                refreshTokens {
//                    // Equivalent to TokenRefreshAuthenticator
//                    val newTokens = client.get {
//                        markAsRefreshTokenRequest()
//                        url("/refreshToken")
//                        parameter(
//                            "refresh_token",
//                            ""//localStorage.getRefreshToken()
//                        )
//                    }.body<Token>()
//                    BearerTokens(
//                        accessToken = newTokens.accessToken,
//                        refreshToken = newTokens.refreshToken
//                    )
//                }
            }
        }
    }

    return client
}