package com.testosterolapp.dogbreeds.breeds

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.testosterolapp.dogbreeds.data.BreedPicture
import com.testosterolapp.dogbreeds.database.DaoRepository

class BreedPicturesViewModel (application: Application) : AndroidViewModel(application) {

    var allBreedPictures: LiveData<PagedList<BreedPicture>>? = null

    fun init(daoRepository: DaoRepository, breedFk: Int) {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(10)
            .setPrefetchDistance(12)
            .build()

        allBreedPictures = LivePagedListBuilder(daoRepository.getBreedPictures(breedFk),
            pagedListConfig)
            .build()

        }
    }

