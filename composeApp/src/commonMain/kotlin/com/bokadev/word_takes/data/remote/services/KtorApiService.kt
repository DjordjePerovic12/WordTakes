package com.bokadev.word_takes.data.remote.services

import com.bokadev.word_takes.core.networking.NetworkError
import com.bokadev.word_takes.core.networking.Resource
import com.bokadev.word_takes.core.networking.addParams
import com.bokadev.word_takes.core.networking.addQueryParams
import com.bokadev.word_takes.core.networking.responseToResource
import com.bokadev.word_takes.data.remote.dto.LoginRequestDto
import com.bokadev.word_takes.data.remote.dto.AuthInfoResponseDto
import com.bokadev.word_takes.data.remote.dto.RegisterRequestDto
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

class KtorApiService(
    private val client: HttpClient
) : ApiService {

    private suspend inline fun <reified T> get(
        path: String = "",
        params: Map<String, String> = mapOf(),
        query: Map<String, String> = mapOf()
    ): Resource<T, NetworkError, String?> {
        return try {
            val modifiedPath = path
                .addParams(params)
                .addQueryParams(query)
            val response = client.get(modifiedPath)

            return responseToResource(response)
        } catch (e: Exception) {
            Resource.Error(NetworkError.NO_INTERNET, e.message)
        }
    }

    private suspend inline fun <reified T, reified B> post(
        path: String = "",
        params: Map<String, String> = mapOf(),
        body: B? = null
    ): Resource<T, NetworkError, String?> {
        return try {
            val modifiedPath = path
                .addParams(params)
            val response = client.post(modifiedPath) {
                if (body != null) {
                    setBody(body)
                }
            }
            return responseToResource(response)
        } catch (e: Exception) {
            Resource.Error(NetworkError.NO_INTERNET, e.message)
        }
    }

    private suspend inline fun <reified T, reified B> put(
        path: String = "",
        params: Map<String, String> = mapOf(),
        body: B? = null
    ): Resource<T, NetworkError, String?> {
        return try {
            val modifiedPath = path
                .addParams(params)
            val response = client.put(modifiedPath) {
                if (body != null) {
                    setBody(body)
                }
            }
            return responseToResource(response)
        } catch (e: Exception) {
            Resource.Error(NetworkError.NO_INTERNET, e.message)
        }
    }

    private suspend inline fun <reified T, reified B> delete(
        path: String = "",
        params: Map<String, String> = mapOf(),
        query: Map<String, String> = mapOf(),
        body: B? = null
    ): Resource<T, NetworkError, String?> {
        return try {
            val modifiedPath = path
                .addParams(params)
                .addQueryParams(query)
            val response = client.delete(modifiedPath) {
                if (body != null) {
                    setBody(body)
                }
            }
            return responseToResource(response)
        } catch (e: Exception) {
            Resource.Error(NetworkError.NO_INTERNET, e.message)
        }
    }
//   TODO add WebSocket functionality
//    private fun sendWSMessage() {}
//    private fun readWSMessage() {}

    override suspend fun login(loginRequestDto: LoginRequestDto): Resource<AuthInfoResponseDto, NetworkError, String?> {
        return post(
            path = "/api/login",
            body = loginRequestDto
        )
    }


    override suspend fun register(registerRequestDto: RegisterRequestDto): Resource<AuthInfoResponseDto, NetworkError, String?> {
        return post(
            path = "/api/register",
            body = registerRequestDto
        )
    }

}