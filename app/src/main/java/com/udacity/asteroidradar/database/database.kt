package com.udacity.asteroidradar.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.domain.Asteroid


@Database(entities = [Asteroid::class], version = 1)
abstract class database() : RoomDatabase() {

}