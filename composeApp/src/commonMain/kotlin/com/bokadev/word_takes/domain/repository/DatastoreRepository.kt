package com.bokadev.word_takes.domain.repository

import com.bokadev.word_takes.domain.model.AuthInfo
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    fun observeAuthInfo(): Flow<AuthInfo?>
    suspend fun set(info: AuthInfo?)
}