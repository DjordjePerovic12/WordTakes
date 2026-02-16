package com.bokadev.word_takes.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.bokadev.core.data.auth.createIosDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformAppModule: Module = module{
    single<DataStore<Preferences>> { createIosDataStore() }
}


