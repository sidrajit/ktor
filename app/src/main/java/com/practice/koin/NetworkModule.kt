package com.practice.koin

import com.practice.network.NetworkClient
import com.practice.network.NetworkRequestImpl
import org.koin.dsl.module

val networkModule = module {
    single { NetworkClient() }
    single { NetworkRequestImpl(get()) }
}