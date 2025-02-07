package com.practice.koin

import com.practice.useCase.GetFactsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetFactsUseCase(get()) }
}