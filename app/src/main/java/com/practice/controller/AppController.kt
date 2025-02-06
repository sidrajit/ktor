package com.practice.controller

import android.app.Application
import com.practice.network.NetworkClient
import com.practice.network.NetworkRequestImpl
import com.practice.ui.mainActivity.MainActivityVM
import com.practice.useCase.GetFactsUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class AppController : Application() {
    private val networkModule = module {
        single { NetworkClient() }
        single { NetworkRequestImpl(get()) }
    }
    private val useCaseModule = module {
        single { GetFactsUseCase(get()) }
    }
    private val viewModelModule = module {
        viewModel { MainActivityVM(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        // initiate koin -->
        startKoin {
            androidContext(this@AppController)
            modules(networkModule)
            modules(useCaseModule)
            modules(viewModelModule)
        }
    }
}