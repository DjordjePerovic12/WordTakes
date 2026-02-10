package com.bokadev.word_takes.data.remote.repository

import com.bokadev.word_takes.core.networking.NetworkError
import com.bokadev.word_takes.core.networking.Resource
import com.bokadev.word_takes.core.networking.map
import com.bokadev.word_takes.data.remote.dto.LoginRequestDto
import com.bokadev.word_takes.data.remote.dto.RegisterRequestDto
import com.bokadev.word_takes.data.remote.mapper.toDomain
import com.bokadev.word_takes.data.remote.services.ApiService
import com.bokadev.word_takes.domain.model.AuthInfo
import com.bokadev.word_takes.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val apiService: ApiService
) : AuthRepository {

    override suspend fun login(body: LoginRequestDto): Resource<AuthInfo, NetworkError, String?> {
        return apiService.login(loginRequestDto = body).map {
            it.toDomain()
        }
    }

    override suspend fun register(body: RegisterRequestDto): Resource<AuthInfo, NetworkError, String?> {
        return apiService.register(registerRequestDto = body)
            .map { it.toDomain() }
    }


}
