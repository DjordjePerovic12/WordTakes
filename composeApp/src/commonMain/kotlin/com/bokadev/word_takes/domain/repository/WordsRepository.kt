package com.bokadev.word_takes.domain.repository

import com.bokadev.word_takes.core.networking.NetworkError
import com.bokadev.word_takes.core.networking.Resource
import com.bokadev.word_takes.data.remote.dto.LoginRequestDto
import com.bokadev.word_takes.data.remote.dto.PostTakeRequestDto
import com.bokadev.word_takes.data.remote.dto.RegisterRequestDto
import com.bokadev.word_takes.domain.model.AuthInfo

interface WordsRepository {
    suspend fun postTake(
        body: PostTakeRequestDto
    ): Resource<Unit, NetworkError, String?>
}