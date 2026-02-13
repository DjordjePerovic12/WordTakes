package com.bokadev.word_takes.data.remote.repository

import com.bokadev.word_takes.core.networking.NetworkError
import com.bokadev.word_takes.core.networking.Resource
import com.bokadev.word_takes.core.networking.map
import com.bokadev.word_takes.data.remote.dto.PostTakeRequestDto
import com.bokadev.word_takes.data.remote.dto.RateWordRequestDto
import com.bokadev.word_takes.data.remote.mapper.toDomain
import com.bokadev.word_takes.data.remote.services.ApiService
import com.bokadev.word_takes.domain.model.PaginatedRatings
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

    override suspend fun rateWord(
        wordId: Int,
        body: RateWordRequestDto
    ): Resource<Unit, NetworkError, String?> {
        return apiService.rateWord(
            wordId = wordId,
            rateWordRequestDto = body
        )
    }

    override suspend fun getAllRatings(wordId: Int, page: Int, perPage: Int): Resource<PaginatedRatings, NetworkError, String?> {
        return apiService.getAllRatings(
            wordId = wordId,
            page = page,
            perPage = perPage
        ).map { it.toDomain() }
    }
}
