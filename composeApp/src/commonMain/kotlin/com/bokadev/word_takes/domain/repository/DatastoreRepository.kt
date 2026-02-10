package com.bokadev.word_takes.domain.repository

import com.bokadev.word_takes.domain.model.LoginResponse
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    fun observeAuthInfo(): Flow<LoginResponse?>
    suspend fun set(info: LoginResponse?)
}