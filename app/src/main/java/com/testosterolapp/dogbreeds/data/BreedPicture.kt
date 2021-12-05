package com.testosterolapp.dogbreeds.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(foreignKeys = [ForeignKey(entity = Breed::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("id_fk_breed"),
    onDelete = ForeignKey.CASCADE)])
class BreedPicture constructor(
    var id_fk_breed: Int?,
    var thumbnail: String?,
    var isFavorite: Boolean?
) {

    @PrimaryKey(autoGenerate = true)
    var id_breed_picture: Int = 0

}
