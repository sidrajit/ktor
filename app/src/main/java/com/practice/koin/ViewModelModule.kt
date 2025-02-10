package com.practice.koin

import com.practice.ui.mainActivity.MainActivityVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityVM(get(),get()) }
}