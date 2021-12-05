package com.testosterolapp.dogbreeds.main

import android.content.Context
import androidx.paging.PagedList
import com.testosterolapp.dogbreeds.data.Breed
import com.testosterolapp.dogbreeds.database.DaoRepository

class MainActivityBoundaryCallbackConstructor(private val context: Context) :  PagedList.BoundaryCallback<Breed?>() {

    override fun onZeroItemsLoaded() {
        fetchServerData(context)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Breed) {
        // not needed in our case, unless we want to optimize the app even more down the line
    }

    private fun fetchServerData(context: Context) {
        val repositoryDao = DaoRepository(context)
        repositoryDao.fetchBreedsData()

    }

}