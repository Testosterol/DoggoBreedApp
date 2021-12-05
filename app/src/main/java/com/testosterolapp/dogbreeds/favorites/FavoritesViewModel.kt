package com.testosterolapp.dogbreeds.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.testosterolapp.dogbreeds.data.BreedPicture
import com.testosterolapp.dogbreeds.database.DaoRepository

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    var allBreedPictures: LiveData<PagedList<BreedPicture>>? = null
    val filterFksAll = MutableLiveData<Int>()

    fun init(daoRepository: DaoRepository) {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .setPrefetchDistance(12)
            .build()

        allBreedPictures = Transformations.switchMap(filterFksAll) { input: Int? ->
            if (input == null || input == 0) {
                return@switchMap LivePagedListBuilder(daoRepository.getListOfBreedPicturesUnFiltered(),
                    pagedListConfig).build()
            } else {
                return@switchMap LivePagedListBuilder(daoRepository.getListOfBreedPicturesFiltered(input),
                    pagedListConfig).build()
            }
        }
    }
}