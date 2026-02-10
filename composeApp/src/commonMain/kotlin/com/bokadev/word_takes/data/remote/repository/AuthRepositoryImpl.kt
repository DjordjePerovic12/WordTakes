package com.bokadev.word_takes.data.remote.repository

import com.bokadev.word_takes.core.networking.NetworkError
import com.bokadev.word_takes.core.networking.Resource
import com.bokadev.word_takes.core.networking.map
import com.bokadev.word_takes.data.remote.dto.LoginRequestDto
import com.bokadev.word_takes.data.remote.mapper.toDomain
import com.bokadev.word_takes.data.remote.services.ApiService
import com.bokadev.word_takes.domain.model.LoginResponse
import com.bokadev.word_takes.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val apiService: ApiService
) : AuthRepository {

    override suspend fun login(body: LoginRequestDto): Resource<LoginResponse, NetworkError, String?> {
        return apiService.login(loginRequestDto = body).map {
            it.toDomain()
        }
    }


}
