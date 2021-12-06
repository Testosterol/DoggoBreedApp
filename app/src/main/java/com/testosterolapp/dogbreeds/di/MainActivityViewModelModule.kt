package com.testosterolapp.dogbreeds.di

import com.testosterolapp.dogbreeds.main.MainActivityViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val MainActivityViewModelModule = module{

    // Specific viewModel pattern to tell Koin how to build MainActivityViewModel
    viewModel {
        MainActivityViewModel(androidApplication())
    }
}