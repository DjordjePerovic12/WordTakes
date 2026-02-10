package com.bokadev.core.data.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import platform.Foundation.*

private const val DATA_STORE_FILE_NAME = "word_takes.preferences_pb"

@OptIn(ExperimentalForeignApi::class)
fun createIosDataStore(): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            val directory = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = true,
                error = null
            ) ?: error("Unable to access iOS documents directory")

            val path = directory
                .URLByAppendingPathComponent(DATA_STORE_FILE_NAME)
                ?.path!!

            path.toPath()
        }
    )
}
