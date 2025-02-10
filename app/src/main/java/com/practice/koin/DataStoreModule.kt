package com.practice.koin

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.preferencesDataStore
import com.practice.localStorage.DataStoreManager
import com.practice.localStorage.DataStorePreferenceKeys
import kotlinx.coroutines.CoroutineExceptionHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val Context.dataStore by preferencesDataStore(name = DataStorePreferenceKeys.DATA_STORE_NAME)

val coRoutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.e("ExceptionCoroutine", throwable.message ?: "Please check DataStoreModule")
}

val dataStoreModule = module {
    single { androidContext().dataStore }
    single { coRoutineExceptionHandler }
    single { DataStoreManager(get(), get()) }
}