package com.practice.controller

import android.app.Application
import com.practice.koin.dataStoreModule
import com.practice.koin.networkModule
import com.practice.koin.useCaseModule
import com.practice.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppController : Application() {
    override fun onCreate() {
        super.onCreate()
        // initiate koin -->
        startKoin {
            androidContext(this@AppController)
            modules(networkModule)
            modules(useCaseModule)
            modules(viewModelModule)
            modules(dataStoreModule)
        }
    }
}