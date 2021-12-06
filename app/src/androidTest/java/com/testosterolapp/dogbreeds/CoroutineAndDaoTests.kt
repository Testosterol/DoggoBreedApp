package com.testosterolapp.dogbreeds

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.testosterolapp.dogbreeds.data.Breed
import com.testosterolapp.dogbreeds.data.BreedPicture
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class CoroutineAndDaoTests : DatabaseTests() {

    @After
    fun teardown() {
        // At the end of all tests, query executor should be idle.
        countingTaskExecutorRule.drainTasks(500, TimeUnit.MILLISECONDS)
        assert(countingTaskExecutorRule.isIdle)
    }

    @Test
    fun testGetListOfFavoriteBreedsFks() {
        runBlocking {
            val breed = Breed("test")
            breedDao.insert(breed)
            val breedPicture = BreedPicture(1, "test", true)
            breedDao.insert(breedPicture)
            val breedDaoFk = breedDao.getListOfFavoriteBreedsFks()
            assertEquals(breedPicture.id_fk_breed, breedDaoFk.get(0))
        }
    }

    @Test
    fun testGetListOfFavoriteBreedsFksMultiple() {
        runBlocking {
            val breed = Breed("breed")
            val breed2 = Breed("breed2")
            val breed3 = Breed("breed3")
            breedDao.insert(breed)
            breedDao.insert(breed2)
            breedDao.insert(breed3)
            val breedPicture = BreedPicture(1, "test", true)
            val breedPicture2 = BreedPicture(2, "test", true)
            val breedPicture3 = BreedPicture(3, "test", true)
            breedDao.insert(breedPicture)
            breedDao.insert(breedPicture2)
            breedDao.insert(breedPicture3)
            val breedDaoFk = breedDao.getListOfFavoriteBreedsFks()
            assertEquals(breedPicture.id_fk_breed, breedDaoFk.get(0))
            assertEquals(breedPicture2.id_fk_breed, breedDaoFk.get(1))
            assertEquals(breedPicture3.id_fk_breed, breedDaoFk.get(2))
        }
    }

    @Test
    fun testGetListOfFavoriteBreedsFksFalse() {
        runBlocking {
            val breed = Breed("test")
            breedDao.insert(breed)
            val breedPicture = BreedPicture(1, "test", false)
            breedDao.insert(breedPicture)
            val breedDaoFk = breedDao.getListOfFavoriteBreedsFks()
            assertEquals(0, breedDaoFk.size)
        }
    }

    @Test
    fun testGetListOfFavoriteBreedsFksUpdate() {
        runBlocking {
            val breed = Breed("test")
            breedDao.insert(breed)
            val breedPicture = BreedPicture(1, "test", false)
            breedDao.insert(breedPicture)
            breedDao.updateFavoriteBreed(true, 1)
            val breedDaoFk = breedDao.getListOfFavoriteBreedsFks()
            assertEquals(breedPicture.id_fk_breed, breedDaoFk.get(0))
        }
    }

    @Test
    fun testGetListOfFavoriteBreedsFksUpdateMultiple() {
        runBlocking {
            val breed = Breed("breed")
            val breed2 = Breed("breed2")
            val breed3 = Breed("breed3")
            breedDao.insert(breed)
            breedDao.insert(breed2)
            breedDao.insert(breed3)
            val breedPicture = BreedPicture(1, "test", true)
            val breedPicture2 = BreedPicture(2, "test", true)
            val breedPicture3 = BreedPicture(3, "test", true)
            breedDao.insert(breedPicture)
            breedDao.insert(breedPicture2)
            breedDao.insert(breedPicture3)
            breedDao.updateFavoriteBreed(false, 2)
            val breedDaoFk = breedDao.getListOfFavoriteBreedsFks()
            val expected = listOf(1,3)
            assertEquals(expected, breedDaoFk)
        }
    }

    @Test
    fun testgetListOfFavoriteBreedNames() {
        runBlocking {
            val breed = Breed("breed")
            breedDao.insert(breed)
            val breedPicture = BreedPicture(1, "test", true)
            breedDao.insert(breedPicture)
            val breedDaoFk = breedDao.getListOfFavoriteBreedNames(listOf(1))
            assertEquals(1, breedDaoFk.size)
        }
    }

    @Test
    fun testgetListOfFavoriteBreedNamesMultiple() {
        runBlocking {
            val breed = Breed("breed")
            val breed2 = Breed("breed2")
            val breed3 = Breed("breed3")
            breedDao.insert(breed)
            breedDao.insert(breed2)
            breedDao.insert(breed3)
            val breedPicture = BreedPicture(1, "test", true)
            val breedPicture2 = BreedPicture(2, "test", true)
            val breedPicture3 = BreedPicture(3, "test", true)
            breedDao.insert(breedPicture)
            breedDao.insert(breedPicture2)
            breedDao.insert(breedPicture3)
            val breedDaoFk = breedDao.getListOfFavoriteBreedNames(listOf(1,2,3))
            assertEquals(3, breedDaoFk.size)
        }
    }

    @Test
    fun testgetListOfFavoriteBreedNamesMultipleString() {
        runBlocking {
            val breed = Breed("breed")
            val breed2 = Breed("breed2")
            val breed3 = Breed("breed3")
            breedDao.insert(breed)
            breedDao.insert(breed2)
            breedDao.insert(breed3)
            val breedPicture = BreedPicture(1, "test", true)
            val breedPicture2 = BreedPicture(2, "test", true)
            val breedPicture3 = BreedPicture(3, "test", true)
            breedDao.insert(breedPicture)
            breedDao.insert(breedPicture2)
            breedDao.insert(breedPicture3)
            val breedDaoFk = breedDao.getListOfFavoriteBreedNames(listOf(1,2,3))
            assertEquals("breed3", breedDaoFk.get(2))
        }
    }

}