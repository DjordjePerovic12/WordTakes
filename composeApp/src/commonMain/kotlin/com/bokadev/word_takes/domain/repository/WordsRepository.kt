package com.bokadev.word_takes.domain.repository

import com.bokadev.word_takes.core.networking.NetworkError
import com.bokadev.word_takes.core.networking.Resource
import com.bokadev.word_takes.data.remote.dto.PostTakeRequestDto
import com.bokadev.word_takes.domain.model.PaginatedWords

interface WordsRepository {
    suspend fun postTake(
        body: PostTakeRequestDto
    ): Resource<Unit, NetworkError, String?>

    suspend fun getAllWords(
        page: Int = 1,
        perPage: Int = 20
    ): Resource<PaginatedWords, NetworkError, String?>
}