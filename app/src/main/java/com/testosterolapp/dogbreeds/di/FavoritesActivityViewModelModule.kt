package com.testosterolapp.dogbreeds.di

import com.testosterolapp.dogbreeds.favorites.FavoritesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val FavoritesActivityViewModelModule = module{

    // Specific viewModel pattern to tell Koin how to build FavoritesViewModel
    viewModel {
        FavoritesViewModel(androidApplication())
    }
}