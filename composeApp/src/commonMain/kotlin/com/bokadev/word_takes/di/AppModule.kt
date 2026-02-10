package com.bokadev.word_takes.di

import com.bokadev.word_takes.MainViewModel
import com.bokadev.word_takes.core.navigation.utils.Navigator
import com.bokadev.word_takes.core.navigation.utils.NavigatorImpl
import com.bokadev.word_takes.data.remote.provideKtorClient
import com.bokadev.word_takes.data.remote.repository.AuthRepositoryImpl
import com.bokadev.word_takes.data.remote.repository.DataStoreRepositoryImpl
import com.bokadev.word_takes.data.remote.repository.WordsRepositoryImpl
import com.bokadev.word_takes.data.remote.services.ApiService
import com.bokadev.word_takes.data.remote.services.KtorApiService
import com.bokadev.word_takes.domain.repository.AuthRepository
import com.bokadev.word_takes.domain.repository.DataStoreRepository
import com.bokadev.word_takes.domain.repository.WordsRepository
import com.bokadev.word_takes.presentation.home.HomeViewModel
import com.bokadev.word_takes.presentation.login.LoginViewModel
import com.bokadev.word_takes.presentation.register.RegisterViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformAppModule: Module


val appModule = module {
    single { provideKtorClient(get(), get<DataStoreRepository>()) }
    single { KtorApiService(get()) }
    single<Navigator> { NavigatorImpl() }
    includes(platformAppModule)
    viewModelOf(::LoginViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::HomeViewModel)
    singleOf(::KtorApiService) bind ApiService::class
    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
    singleOf(::WordsRepositoryImpl) bind WordsRepository::class
    singleOf(::DataStoreRepositoryImpl) bind DataStoreRepository::class
}
