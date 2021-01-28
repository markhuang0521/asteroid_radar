package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.udacity.asteroidradar.util.Converters


@Database(entities = [DbAsteroid::class, DbPictureOfDay::class], version = 7)
@TypeConverters(Converters::class)

abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
    abstract val picOfDayDao: PicOfDayDao


}

private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AsteroidDatabase::class.java,
                "asteroids"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}