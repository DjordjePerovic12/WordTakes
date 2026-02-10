package com.bokadev.word_takes.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

private val Context.wordTakesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "word_takes"
)
actual val platformAppModule: Module = module{
    single<DataStore<Preferences>> { androidContext().wordTakesDataStore }
}
