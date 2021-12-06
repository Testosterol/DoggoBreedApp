package com.testosterolapp.dogbreeds

import com.testosterolapp.dogbreeds.data.Breed
import com.testosterolapp.dogbreeds.data.BreedPicture
import com.testosterolapp.dogbreeds.serverCommunication.Url
import org.junit.Assert
import org.junit.Test

class UnitTests {

    @Test
    fun testIsUrlCorrect() {
        val url = Url.URL_DATA
        Assert.assertEquals(url, "https://dog.ceo/api/breeds/list/all")
    }

    @Test
    fun testIsUrlCorrectFail() {
        val url = Url.URL_DATA
        Assert.assertNotEquals(url, "https://dog.ceo/api/breeds/pictures")
    }

    @Test
    fun testBreedName1() {
        val breed = Breed("Shepperd")
        Assert.assertEquals(breed.breedName, "Shepperd")
    }

    @Test
    fun testBreedName2() {
        val breed = Breed("Husky")
        Assert.assertEquals(breed.breedName, "Husky")
    }

    @Test
    fun testBreedName3() {
        val breed = Breed("Wolf")
        Assert.assertEquals(breed.breedName, "Wolf")
    }

    @Test
    fun testBreedNameFail() {
        val breed = Breed("Wolf")
        Assert.assertNotEquals(breed.breedName, "Husky")
    }

    @Test
    fun testBreedPictureFk() {
        val breedPicture = BreedPicture(1, "", false)
        Assert.assertEquals(breedPicture.id_fk_breed, 1)
    }

    @Test
    fun testBreedPictureThumbnail() {
        val breedPicture = BreedPicture(1, "https://images.dog.ceo/breeds/clumber/n02101556_1018.jpg", false)
        Assert.assertEquals(breedPicture.thumbnail, "https://images.dog.ceo/breeds/clumber/n02101556_1018.jpg")
    }


    @Test
    fun testBreedPictureIsFavorite() {
        val breedPicture = BreedPicture(1, "", true)
        Assert.assertEquals(breedPicture.isFavorite, true)
    }

}