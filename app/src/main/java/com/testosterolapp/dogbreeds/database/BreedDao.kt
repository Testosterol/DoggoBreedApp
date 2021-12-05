package com.testosterolapp.dogbreeds.database

import androidx.paging.DataSource
import androidx.room.*
import com.testosterolapp.dogbreeds.data.Breed
import com.testosterolapp.dogbreeds.data.BreedPicture

@Dao
interface BreedDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(breed: Breed)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(breedPicture: BreedPicture)

    @Query("UPDATE breedpicture SET isFavorite = :isFavorite WHERE id_breed_picture = :breedPictureId")
    fun updateFavoriteBreed(isFavorite: Boolean, breedPictureId: Int)

    @Query("SELECT * FROM breed ORDER BY breedName ASC ")
    fun getBreedsInSortedOrder(): DataSource.Factory<Int, Breed>

    @Query("SELECT * FROM breedpicture WHERE id_fk_breed = :breedFk")
    fun getBreedPictures(breedFk: Int): DataSource.Factory<Int, BreedPicture>

    @Query("SELECT DISTINCT(id_fk_breed) FROM breedpicture WHERE isFavorite = 1 ")
    fun getListOfFavoriteBreedsFks(): List<Int>

    @Query("SELECT breedName FROM breed WHERE id IN (:breedsFkList) ")
    fun getListOfFavoriteBreedNames(breedsFkList: List<Int>): List<String>

    @Query("SELECT * FROM breedpicture WHERE id_fk_breed LIKE :fk AND isFavorite = 1")
    fun getListOfBreedPicturesFiltered(fk: Int): DataSource.Factory<Int, BreedPicture>

    @Query("SELECT * FROM breedpicture WHERE isFavorite = 1")
    fun getListOfBreedPicturesUnFiltered(): DataSource.Factory<Int, BreedPicture>


}