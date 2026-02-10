package com.bokadev.word_takes.data.remote.services

import com.bokadev.word_takes.core.networking.DomainError
import com.bokadev.word_takes.core.networking.NetworkError
import com.bokadev.word_takes.core.networking.Resource
import com.bokadev.word_takes.data.remote.dto.LoginRequestDto
import com.bokadev.word_takes.data.remote.dto.LoginResponseDto

interface ApiService {

    suspend fun login(
        loginRequestDto: LoginRequestDto
    ): Resource<LoginResponseDto, NetworkError, String?>

}