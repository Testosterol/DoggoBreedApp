package com.testosterolapp.dogbreeds

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.testosterolapp.dogbreeds.database.BreedDao
import com.testosterolapp.dogbreeds.database.Database
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.util.concurrent.TimeUnit

abstract class DatabaseTests {
    @Rule

    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()
    protected lateinit var database: Database
    protected lateinit var breedDao: BreedDao

    @Before
    @Throws(Exception::class)
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            Database::class.java)
            .build()
        breedDao = database.breedDao()!!
    }
    @After
    @Throws(Exception::class)
    fun tearDown() {
        database.close()
    }

    fun drain() {
        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)
    }
}