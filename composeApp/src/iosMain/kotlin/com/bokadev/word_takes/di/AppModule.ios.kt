package com.bokadev.word_takes.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.bokadev.core.data.auth.createIosDataStore
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual val platformAppModule: Module = module{
    single<DataStore<Preferences>> { createIosDataStore() }
}


