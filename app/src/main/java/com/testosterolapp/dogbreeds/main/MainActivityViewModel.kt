package com.testosterolapp.dogbreeds.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.testosterolapp.dogbreeds.data.Breed
import com.testosterolapp.dogbreeds.database.DaoRepository

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    var allBreeds: LiveData<PagedList<Breed>>? = null
    val filterBreedAll = MutableLiveData<String>()
    private val repositoryBoundaryCallback = MainActivityBoundaryCallbackConstructor(application)

    fun init(daoRepository: DaoRepository) {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(10)
            .setPrefetchDistance(12)
            .build()

        allBreeds = Transformations.switchMap(filterBreedAll) {
            return@switchMap LivePagedListBuilder(daoRepository.getBreedsInSortedOrder(),
                    pagedListConfig).setBoundaryCallback(repositoryBoundaryCallback)
                    .build()
            }

    }
}