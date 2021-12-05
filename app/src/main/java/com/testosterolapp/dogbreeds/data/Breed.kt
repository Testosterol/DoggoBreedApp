package com.testosterolapp.dogbreeds.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Breed {


    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var breedName: String? = null

    @Ignore
    var breedPicture: BreedPicture? = null

    constructor()


    constructor(breedName: String?){
        this.breedName = breedName
    }
}