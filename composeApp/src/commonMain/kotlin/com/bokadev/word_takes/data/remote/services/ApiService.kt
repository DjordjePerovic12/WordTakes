package com.bokadev.word_takes.data.remote.services

import com.bokadev.word_takes.core.networking.NetworkError
import com.bokadev.word_takes.core.networking.Resource
import com.bokadev.word_takes.data.remote.dto.LoginRequestDto
import com.bokadev.word_takes.data.remote.dto.AuthInfoResponseDto
import com.bokadev.word_takes.data.remote.dto.PostTakeRequestDto
import com.bokadev.word_takes.data.remote.dto.RegisterRequestDto

interface ApiService {

    suspend fun login(
        loginRequestDto: LoginRequestDto
    ): Resource<AuthInfoResponseDto, NetworkError, String?>

    suspend fun register(
        registerRequestDto: RegisterRequestDto
    ): Resource<AuthInfoResponseDto, NetworkError, String?>


    suspend fun postTake(
        postTakeRequestDto: PostTakeRequestDto,
    ): Resource<Unit, NetworkError, String?>

}