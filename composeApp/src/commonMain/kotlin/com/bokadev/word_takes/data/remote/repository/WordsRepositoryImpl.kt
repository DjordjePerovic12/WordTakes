package com.bokadev.word_takes.data.remote.repository

import com.bokadev.word_takes.core.networking.NetworkError
import com.bokadev.word_takes.core.networking.Resource
import com.bokadev.word_takes.data.remote.dto.PostTakeRequestDto
import com.bokadev.word_takes.data.remote.services.ApiService
import com.bokadev.word_takes.domain.repository.WordsRepository

class WordsRepositoryImpl(
    private val apiService: ApiService
) : WordsRepository {

    override suspend fun postTake(body: PostTakeRequestDto): Resource<Unit, NetworkError, String?> {
        return apiService.postTake(postTakeRequestDto = body)
    }
}
