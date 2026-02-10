package com.bokadev.word_takes.data.remote.repository

import com.bokadev.word_takes.core.networking.NetworkError
import com.bokadev.word_takes.core.networking.Resource
import com.bokadev.word_takes.core.networking.map
import com.bokadev.word_takes.data.remote.dto.PostTakeRequestDto
import com.bokadev.word_takes.data.remote.mapper.toDomain
import com.bokadev.word_takes.data.remote.services.ApiService
import com.bokadev.word_takes.domain.model.PaginatedWords
import com.bokadev.word_takes.domain.repository.WordsRepository
import toDomain

class WordsRepositoryImpl(
    private val apiService: ApiService
) : WordsRepository {

    override suspend fun postTake(body: PostTakeRequestDto): Resource<Unit, NetworkError, String?> {
        return apiService.postTake(postTakeRequestDto = body)
    }

    override suspend fun getAllWords(
        page: Int, perPage: Int
    ): Resource<PaginatedWords, NetworkError, String?> {
        return apiService.getAllWords(
            page, perPage
        ).map { it.toDomain() }
    }
}
