package com.bokadev.word_takes.data.remote.services

import com.bokadev.word_takes.core.networking.DomainError
import com.bokadev.word_takes.core.networking.Resource

interface ApiService {

    suspend fun test(): Resource<Unit, DomainError, String?>
}