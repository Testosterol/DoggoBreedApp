package com.testosterolapp.dogbreeds.core

import android.app.Application
import com.testosterolapp.dogbreeds.di.BreedPicturesActivityViewModelModule
import com.testosterolapp.dogbreeds.di.FavoritesActivityViewModelModule
import com.testosterolapp.dogbreeds.di.MainActivityViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class BreedsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BreedsApplication)
            modules(
                MainActivityViewModelModule,
                BreedPicturesActivityViewModelModule,
                FavoritesActivityViewModelModule
            )
        }
    }
}