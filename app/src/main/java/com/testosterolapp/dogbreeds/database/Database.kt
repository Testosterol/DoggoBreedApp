package com.testosterolapp.dogbreeds.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.testosterolapp.dogbreeds.data.Breed
import com.testosterolapp.dogbreeds.data.BreedPicture

@androidx.room.Database(entities = [Breed::class, BreedPicture::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun breedDao(): BreedDao?


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: Database? = null

        fun getDatabase(context: Context): Database {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    Database::class.java, "db_breeds")
                    .allowMainThreadQueries() // Needed due to UI work
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}