package com.testosterolapp.dogbreeds.di

import com.testosterolapp.dogbreeds.breeds.BreedPicturesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val BreedPicturesActivityViewModelModule = module{

    // Specific viewModel pattern to tell Koin how to build BreedPicturesViewModel
    viewModel {
        BreedPicturesViewModel(androidApplication())
    }
}