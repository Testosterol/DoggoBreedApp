package com.testosterolapp.dogbreeds.database

import android.content.Context
import androidx.paging.DataSource
import com.testosterolapp.dogbreeds.data.Breed
import com.testosterolapp.dogbreeds.data.BreedPicture
import com.testosterolapp.dogbreeds.serverCommunication.GetServerData

class DaoRepository {

    var breedDao: BreedDao? = null
    var mContext: Context? = null

    constructor(context: Context) : this() {
        this.mContext = context
        val database = Database.getDatabase(context)
        this.breedDao = database.breedDao()!!
    }

    constructor()

    fun fetchBreedsData() {
        val getServerData = GetServerData()
        getServerData.getBreedsData(mContext)
    }

    fun fetchBreedPicturesData(fk: Int, breedName: String) {
        val getServerData = GetServerData()
        getServerData.getBreedPicturesData(mContext, breedName, fk)
    }

    suspend fun insertBreed(breed: Breed) {
        return breedDao?.insert(breed)!!
    }

    suspend fun insertPictureOfBreed(breedPicture: BreedPicture) {
        return breedDao?.insert(breedPicture)!!
    }

    fun updateFavoriteBreed(isFavorite: Boolean, breedId: Int) {
        return breedDao?.updateFavoriteBreed(isFavorite, breedId)!!
    }

    fun getListOfFavoriteBreeds(): List<Int>? {
        return breedDao?.getListOfFavoriteBreedsFks()
    }

    fun getListOfFavoriteBreedNames(breedsFkList: List<Int>): List<String>? {
        return breedDao?.getListOfFavoriteBreedNames(breedsFkList)
    }

    fun getBreedsInSortedOrder(): DataSource.Factory<Int, Breed> {
        return breedDao?.getBreedsInSortedOrder()!!
    }

    fun getBreedPictures(breedFk: Int): DataSource.Factory<Int, BreedPicture> {
        return breedDao?.getBreedPictures(breedFk)!!
    }

    fun getListOfBreedPicturesFiltered(fk: Int): DataSource.Factory<Int, BreedPicture> {
        return breedDao?.getListOfBreedPicturesFiltered(fk)!!
    }

    fun getListOfBreedPicturesUnFiltered(): DataSource.Factory<Int, BreedPicture> {
        return breedDao?.getListOfBreedPicturesUnFiltered()!!
    }


}