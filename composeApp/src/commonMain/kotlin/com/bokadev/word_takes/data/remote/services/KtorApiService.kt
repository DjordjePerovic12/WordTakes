package com.bokadev.word_takes.data.remote.services

import com.bokadev.word_takes.core.networking.NetworkError
import com.bokadev.word_takes.core.networking.Resource
import com.bokadev.word_takes.core.networking.addParams
import com.bokadev.word_takes.core.networking.addQueryParams
import com.bokadev.word_takes.core.networking.responseToResource
import com.bokadev.word_takes.data.remote.dto.AuthInfoResponseDto
import com.bokadev.word_takes.data.remote.dto.LoginRequestDto
import com.bokadev.word_takes.data.remote.dto.PaginatedRatingsResponseDto
import com.bokadev.word_takes.data.remote.dto.PostTakeRequestDto
import com.bokadev.word_takes.data.remote.dto.RateWordRequestDto
import com.bokadev.word_takes.data.remote.dto.RegisterRequestDto
import com.bokadev.word_takes.data.remote.dto.WordsPageResponseDto
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

    override suspend fun postTake(postTakeRequestDto: PostTakeRequestDto): Resource<Unit, NetworkError, String?> {
        return post(
            path = "/api/words",
            body = postTakeRequestDto
        )
    }

    override suspend fun getAllWords(
        page: Int,
        perPage: Int
    ): Resource<WordsPageResponseDto, NetworkError, String?> {
        return get(
            path = "/api/words",
            query = mapOf(
                "page" to page.toString(),
                "per_page" to perPage.toString()
            )
        )
    }

    override suspend fun rateWord(
        wordId: Int,
        rateWordRequestDto: RateWordRequestDto
    ): Resource<Unit, NetworkError, String?> {
        return post(
            path = "/api/words/{wordPost}/rate",
            params = mapOf(
                "wordPost" to wordId.toString(),
            ),
            body = rateWordRequestDto
        )
    }


    override suspend fun getAllRatings(
        wordId: Int,
        page: Int,
        perPage: Int,
    ): Resource<PaginatedRatingsResponseDto, NetworkError, String?> {
        return get(
            path = "/api/words/{wordPost}/ratings",
            params = mapOf(
                "wordPost" to wordId.toString(),
            ),
            query = mapOf(
                "page" to page.toString(),
                "per_page" to perPage.toString()
            )
        )
    }


    override suspend fun getWordsByUserId(
        userId: Int,
        page: Int,
        perPage: Int
    ): Resource<WordsPageResponseDto, NetworkError, String?> {
        return get(
            path = "/api/users/{user}/words",
            params = mapOf(
                "user" to userId.toString(),
            ),
            query = mapOf(
                "page" to page.toString(),
                "per_page" to perPage.toString()
            )
        )
    }
}